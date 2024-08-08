package com.tinqinacademy.comments.core.processors.hotel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.tinqinacademy.comments.api.exceptions.Errors;
import com.tinqinacademy.comments.api.operations.hotel.usereditowncomment.UserEditOwnCommentInput;
import com.tinqinacademy.comments.api.operations.hotel.usereditowncomment.UserEditOwnCommentOperation;
import com.tinqinacademy.comments.api.operations.hotel.usereditowncomment.UserEditOwnCommentOutput;
import com.tinqinacademy.comments.api.operations.system.admineditanycomment.AdminEditAnyCommentOutput;
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

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Slf4j
@Service
public class UserEditOwnCommentOperationProcessor extends BaseOperationProcessor implements UserEditOwnCommentOperation {

    private final CommentsRepository commentsRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserEditOwnCommentOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper, Validator validator, CommentsRepository commentsRepository, ObjectMapper objectMapper) {
        super(conversionService, errorMapper, validator);
        this.commentsRepository = commentsRepository;
        this.objectMapper = objectMapper;
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

                    Comment comment = commentOptional.get();

                    JsonNode commentNode = objectMapper.valueToTree(comment);

                    JsonNode inputNode = objectMapper.valueToTree(input);

                    try {
                        JsonMergePatch patch = JsonMergePatch.fromJson(inputNode);
                        JsonNode patchedNode = patch.apply(commentNode);

                        Comment patchedComment = objectMapper.treeToValue(patchedNode, Comment.class);

                        Comment save = commentsRepository.save(patchedComment);

                        UserEditOwnCommentOutput output = UserEditOwnCommentOutput.builder()
                                .id(String.valueOf(save.getCommentId()))
                                .build();

                        return output;
                    } catch (JsonPatchException | IOException e) {
                        throw new RuntimeException("Failed to apply patch to comment: " + e.getMessage(), e);
                    }
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(instanceOf(IllegalArgumentException.class)),
                                errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
                ));
    }
}
