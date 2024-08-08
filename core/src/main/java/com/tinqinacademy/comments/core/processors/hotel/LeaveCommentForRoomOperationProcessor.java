package com.tinqinacademy.comments.core.processors.hotel;

import com.tinqinacademy.comments.api.exceptions.Errors;
import com.tinqinacademy.comments.api.operations.hotel.leavecommentforroom.LeaveCommentForRoomInput;
import com.tinqinacademy.comments.api.operations.hotel.leavecommentforroom.LeaveCommentForRoomOperation;
import com.tinqinacademy.comments.api.operations.hotel.leavecommentforroom.LeaveCommentForRoomOutput;
import com.tinqinacademy.comments.core.converters.LeaveCommentForRoomOutputConverter;
import com.tinqinacademy.comments.core.errorhandling.ErrorMapper;
import com.tinqinacademy.comments.core.processors.BaseOperationProcessor;
import com.tinqinacademy.comments.persistence.entities.Comment;
import com.tinqinacademy.comments.persistence.repositories.CommentsRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Slf4j
@Service
public class LeaveCommentForRoomOperationProcessor extends BaseOperationProcessor implements LeaveCommentForRoomOperation {
    private final CommentsRepository commentsRepository;

    public LeaveCommentForRoomOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper, Validator validator, CommentsRepository commentsRepository) {
        super(conversionService, errorMapper, validator);
        this.commentsRepository = commentsRepository;
    }

    @Override
    public Either<Errors, LeaveCommentForRoomOutput> process(LeaveCommentForRoomInput input) {
        return validateInput(input)
                .flatMap(valid -> getAdminPartialUpdateOutputs(input));
    }

    private Either<Errors, LeaveCommentForRoomOutput> getAdminPartialUpdateOutputs(LeaveCommentForRoomInput input) {
        return Try.of(() -> {
                    log.info("Start leaveCommentForRoom input: {}", input);

                    Comment comment = conversionService.convert(input, Comment.CommentBuilder.class)
                            .build();

                    Comment save = commentsRepository.save(comment);

                    LeaveCommentForRoomOutput output = LeaveCommentForRoomOutput.builder()
                            .id(String.valueOf(save.getCommentId()))
                            .build();

                    log.info("End leaveCommentForRoom output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(instanceOf(IllegalArgumentException.class)),
                                errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
                ));
    }
}
