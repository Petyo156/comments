package com.tinqinacademy.comments.api.models.admindeleteanycomment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class AdminDeleteAnyCommentInput {
    @JsonIgnore
    private String commentId;
}
