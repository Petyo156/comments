package com.tinqinacademy.comments.api.models.leavecommentforroom;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class LeaveCommentForRoomInput {
    @NotBlank(message = "roomId can not be blank")
    private String roomId;
    @NotBlank(message = "firstName can not be blank")
    private String firstName;
    @NotBlank(message = "lastName can not be blank")
    private String lastName;
    @NotBlank(message = "content can not be blank")
    private String content;
}
