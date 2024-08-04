package com.tinqinacademy.comments.core.processors;

import com.tinqinacademy.comments.api.base.OperationInput;
import com.tinqinacademy.comments.api.exceptions.ErrorResponse;
import com.tinqinacademy.comments.core.errorhandling.ErrorMapper;
import com.tinqinacademy.comments.persistence.entities.Comment;
import io.vavr.control.Either;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import com.tinqinacademy.comments.api.exceptions.Errors;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


public class BaseOperationProcessor {
    protected final ConversionService conversionService;
    protected final ErrorMapper errorMapper;
    protected final Validator validator;

    @Autowired
    public BaseOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper, Validator validator) {
        this.conversionService = conversionService;
        this.errorMapper = errorMapper;
        this.validator = validator;
    }

    protected Either<Errors, ? extends OperationInput> validateInput(OperationInput input) {
        Set<ConstraintViolation<OperationInput>> violations = validator.validate(input);

        if (!violations.isEmpty()) {
            List<String> errorMessages = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            ErrorResponse errorResponse = ErrorResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(String.join(", ", errorMessages))
                    .build();

            return Either.left(errorResponse);
        }

        return Either.right(input);
    }

    protected void throwIfCommentDoesntExist(Optional<Comment> comment) {
        if (comment.isEmpty()) {
            throw new IllegalArgumentException("Comment with given ID doesn't exist!");
        }
    }

    protected void throwIfCommentsForRoomDontExist(List<Comment> commentList) {
        if (commentList.isEmpty()) {
            throw new IllegalArgumentException("Comments for given RoomID do not exist!");
        }
    }

}
