package com.tinqinacademy.comments.api.exceptions;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ErrorWrapper implements Errors {
    private List<ErrorResponse> errorResponseList;
    private LocalDate date;
}
