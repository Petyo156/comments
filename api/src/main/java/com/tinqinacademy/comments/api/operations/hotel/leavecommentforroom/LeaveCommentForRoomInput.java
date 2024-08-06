package com.tinqinacademy.comments.api.operations.hotel.leavecommentforroom;

import com.tinqinacademy.comments.api.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class LeaveCommentForRoomInput implements OperationInput {
    @NotBlank(message = "roomId can not be blank")

    private String roomId;
    @NotBlank(message = "firstName can not be blank")
    private String firstName;
    @NotBlank(message = "lastName can not be blank")
    private String lastName;
    @NotBlank(message = "content can not be blank")
    private String content;
}
