package com.tinqinacademy.comments.core.errorhandling;

import com.tinqinacademy.comments.api.exceptions.ErrorResponse;
import com.tinqinacademy.comments.api.exceptions.ErrorWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ErrorMapper {
    public ErrorWrapper handleError(Throwable ex, HttpStatus httpStatus) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(ex.getMessage())
                .httpStatus(httpStatus)
                .build();

        return ErrorWrapper.builder()
                .errorResponseList(List.of(errorResponse))
                .date(LocalDate.now())
                .build();
    }
}
