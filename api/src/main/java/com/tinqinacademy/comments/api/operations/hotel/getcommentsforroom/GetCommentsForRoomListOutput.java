package com.tinqinacademy.comments.api.operations.hotel.getcommentsforroom;

import com.tinqinacademy.comments.api.base.OperationOutput;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class GetCommentsForRoomListOutput implements OperationOutput {
    private List<GetCommentsForRoomOutput> list;
}
