package com.tinqinacademy.comments.rest.controllers;

import com.tinqinacademy.comments.api.base.OperationOutput;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.tinqinacademy.comments.api.exceptions.Errors;

@RestController
public abstract class BaseController {
    public ResponseEntity<?> handleOperation(Either<Errors, ? extends OperationOutput> result, HttpStatus httpStatus) {
        return result.isLeft()
                ? ResponseEntity.status(httpStatus).body(result.getLeft())
                : ResponseEntity.ok(result.get());
    }


}