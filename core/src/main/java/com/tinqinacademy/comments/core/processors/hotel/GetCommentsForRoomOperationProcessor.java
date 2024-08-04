package com.tinqinacademy.comments.core.processors.hotel;

import com.tinqinacademy.comments.api.exceptions.Errors;
import com.tinqinacademy.comments.api.operations.hotel.getcommentsforroom.GetCommentsForRoomInput;
import com.tinqinacademy.comments.api.operations.hotel.getcommentsforroom.GetCommentsForRoomListOutput;
import com.tinqinacademy.comments.api.operations.hotel.getcommentsforroom.GetCommentsForRoomOperation;
import com.tinqinacademy.comments.api.operations.hotel.getcommentsforroom.GetCommentsForRoomOutput;
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

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Slf4j
@Service
public class GetCommentsForRoomOperationProcessor extends BaseOperationProcessor implements GetCommentsForRoomOperation {
    private final CommentsRepository commentsRepository;

    @Autowired
    public GetCommentsForRoomOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper, Validator validator,
                                                CommentsRepository commentsRepository) {
        super(conversionService, errorMapper, validator);
        this.commentsRepository = commentsRepository;
    }

    @Override
    public Either<Errors, GetCommentsForRoomListOutput> process(GetCommentsForRoomInput input) {
        return validateInput(input)
                .flatMap(valid -> getCommentsForRoomListOutputs(input));
    }

    private Either<Errors, GetCommentsForRoomListOutput> getCommentsForRoomListOutputs(GetCommentsForRoomInput input) {
        return Try.of(() -> {
                    log.info("Start getCommentsForRoom input: {}", input);

                    List<Comment> commentList = commentsRepository.findByRoomId(UUID.fromString(input.getRoomId()));

                    throwIfCommentsForRoomDontExist(commentList);

                    List<GetCommentsForRoomOutput> outputs = commentList.stream()
                            .map(comment -> GetCommentsForRoomOutput.builder()
                                    .publishDate(comment.getCreationDate())
                                    .firstName(comment.getFirstName())
                                    .lastName(comment.getLastName())
                                    .id(String.valueOf(comment.getCommentId()))
                                    .lastEditedDate(comment.getLastEditDate())
                                    .content(comment.getContent())
                                    //hardcoded - have to merge hotel and comments first
                                    .lastEditedBy(String.valueOf(UUID.randomUUID()))
                                    .build())
                            .collect(Collectors.toList());

                    GetCommentsForRoomListOutput finalOutput = GetCommentsForRoomListOutput.builder()
                            .list(outputs)
                            .build();

                    log.info("End getCommentsForRoom output: {}", finalOutput);
                    return finalOutput;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(instanceOf(IllegalArgumentException.class)),
                                errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
                ));
    }
}

