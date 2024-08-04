package com.tinqinacademy.comments.api.operations.hotel.leavecommentforroom;

import com.tinqinacademy.comments.api.base.OperationOutput;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class LeaveCommentForRoomOutput implements OperationOutput {
    private String id;
}
