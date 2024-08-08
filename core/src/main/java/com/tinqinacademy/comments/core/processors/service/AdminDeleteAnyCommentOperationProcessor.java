package com.tinqinacademy.comments.core.processors.service;

import com.tinqinacademy.comments.api.exceptions.Errors;
import com.tinqinacademy.comments.api.operations.hotel.usereditowncomment.UserEditOwnCommentInput;
import com.tinqinacademy.comments.api.operations.hotel.usereditowncomment.UserEditOwnCommentOutput;
import com.tinqinacademy.comments.api.operations.system.admindeleteanycomment.AdminDeleteAnyCommentInput;
import com.tinqinacademy.comments.api.operations.system.admindeleteanycomment.AdminDeleteAnyCommentOperation;
import com.tinqinacademy.comments.api.operations.system.admindeleteanycomment.AdminDeleteAnyCommentOutput;
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
import java.util.Optional;
import java.util.UUID;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Slf4j
@Service
public class AdminDeleteAnyCommentOperationProcessor extends BaseOperationProcessor implements AdminDeleteAnyCommentOperation {

    private final CommentsRepository commentsRepository;

    @Autowired
    public AdminDeleteAnyCommentOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper, Validator validator, CommentsRepository commentsRepository) {
        super(conversionService, errorMapper, validator);
        this.commentsRepository = commentsRepository;
    }

    @Override
    public Either<Errors, AdminDeleteAnyCommentOutput> process(AdminDeleteAnyCommentInput input) {
        return validateInput(input)
                .flatMap(valid -> adminDeleteAnyCommentOutputs(input));
    }

    private Either<Errors, AdminDeleteAnyCommentOutput> adminDeleteAnyCommentOutputs(AdminDeleteAnyCommentInput input) {
        return Try.of(() -> {
                    log.info("Start adminDeleteAnyComment input: {}", input);

                    Optional<Comment> commentOptional = commentsRepository.findByCommentId(UUID.fromString(input.getCommentId()));
                    throwIfCommentDoesntExist(commentOptional);

                    commentsRepository.delete(commentOptional.get());

                    AdminDeleteAnyCommentOutput output = AdminDeleteAnyCommentOutput.builder()
                            .build();

                    log.info("End adminEditAnyComment output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(instanceOf(IllegalArgumentException.class)),
                                errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
                ));
    }
}
