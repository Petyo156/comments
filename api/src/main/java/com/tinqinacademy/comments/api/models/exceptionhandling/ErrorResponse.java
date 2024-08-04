package com.tinqinacademy.comments.api.models.exceptionhandling;

import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ErrorResponse {
    private String message;
    private HttpStatus httpStatus;
}
