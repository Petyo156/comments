package com.tinqinacademy.comments.api.models.getcommentsforroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
public class GetCommentsForRoomInput {
    @JsonIgnore
    private String roomId;
}
