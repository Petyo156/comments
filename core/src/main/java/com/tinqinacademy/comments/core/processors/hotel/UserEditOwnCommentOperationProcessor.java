package com.tinqinacademy.comments.core.processors.hotel;

import com.tinqinacademy.comments.api.exceptions.Errors;
import com.tinqinacademy.comments.api.operations.hotel.leavecommentforroom.LeaveCommentForRoomInput;
import com.tinqinacademy.comments.api.operations.hotel.leavecommentforroom.LeaveCommentForRoomOutput;
import com.tinqinacademy.comments.api.operations.hotel.usereditowncomment.UserEditOwnCommentInput;
import com.tinqinacademy.comments.api.operations.hotel.usereditowncomment.UserEditOwnCommentOperation;
import com.tinqinacademy.comments.api.operations.hotel.usereditowncomment.UserEditOwnCommentOutput;
import com.tinqinacademy.comments.core.errorhandling.ErrorMapper;
import com.tinqinacademy.comments.core.processors.BaseOperationProcessor;
import com.tinqinacademy.comments.persistence.entities.Comment;
import com.tinqinacademy.comments.persistence.repositories.CommentsRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Slf4j
@Service
public class UserEditOwnCommentOperationProcessor extends BaseOperationProcessor implements UserEditOwnCommentOperation {

    private final CommentsRepository commentsRepository;

    @Autowired
    public UserEditOwnCommentOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper, Validator validator, CommentsRepository commentsRepository) {
        super(conversionService, errorMapper, validator);
        this.commentsRepository = commentsRepository;
    }

    @Override
    public Either<Errors, UserEditOwnCommentOutput> process(UserEditOwnCommentInput input) {
        return validateInput(input)
                .flatMap(valid -> userEditOwnCommentOutputs(input));
    }

    private Either<Errors, UserEditOwnCommentOutput> userEditOwnCommentOutputs(UserEditOwnCommentInput input) {
        return Try.of(() -> {
                    log.info("Start userCanEditOwnComment input: {}", input);

                    Optional<Comment> commentOptional = commentsRepository.findByCommentId(UUID.fromString(input.getCommentId()));
                    throwIfCommentDoesntExist(commentOptional);

                    Comment comment = commentOptional.get().toBuilder()
                            .content(input.getContent())
                            .lastEditDate(LocalDate.now())
                            .build();

                    commentsRepository.save(comment);

                    UserEditOwnCommentOutput output = UserEditOwnCommentOutput.builder()
                            .id(String.valueOf(comment.getCommentId()))
                            .build();

                    log.info("End userCanEditOwnComment output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(instanceOf(IllegalArgumentException.class)),
                                errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
                ));
    }
}
