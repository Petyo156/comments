package com.tinqinacademy.comments.api.models.usereditowncomment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class UserEditOwnCommentInput {
    @NotBlank(message = "content can not be blank")
    private String content;
    @JsonIgnore
    private String commentId;
}
