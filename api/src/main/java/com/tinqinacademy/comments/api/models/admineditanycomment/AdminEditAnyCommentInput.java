package com.tinqinacademy.comments.api.models.admineditanycomment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class AdminEditAnyCommentInput {
    @JsonIgnore
    private String commentId;
    @NotBlank(message = "roomNo cant be blank")
    @Size(min = 1, max = 3, message = "roomNo 1-3 size")
    private String roomNo;
    @NotBlank(message = "firstName cant be blank")
    private String firstName;
    @NotBlank(message = "lastName cant be blank")
    private String lastName;
    @NotBlank(message = "content cant be blank")
    private String content;
}
