package com.tinqinacademy.comments.api.operations.system.admindeleteanycomment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.comments.api.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class AdminDeleteAnyCommentInput implements OperationInput {
    @JsonIgnore
    private String commentId;
}
