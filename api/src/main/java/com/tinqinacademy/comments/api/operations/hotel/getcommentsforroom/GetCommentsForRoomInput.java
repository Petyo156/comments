package com.tinqinacademy.comments.api.operations.hotel.getcommentsforroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.comments.api.base.OperationInput;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class GetCommentsForRoomInput implements OperationInput {
    @JsonIgnore
    private String roomId;
}
