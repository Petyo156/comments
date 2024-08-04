package com.tinqinacademy.comments.api.operations.hotel.usereditowncomment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.comments.api.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class UserEditOwnCommentInput implements OperationInput {
    @NotBlank(message = "content can not be blank")
    private String content;
    @JsonIgnore
    private String commentId;
}
