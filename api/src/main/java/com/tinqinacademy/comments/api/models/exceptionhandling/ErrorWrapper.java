package com.tinqinacademy.comments.api.models.exceptionhandling;

import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ErrorWrapper {
    private List<ErrorResponse> errorResponseList;
    private Date date;
}