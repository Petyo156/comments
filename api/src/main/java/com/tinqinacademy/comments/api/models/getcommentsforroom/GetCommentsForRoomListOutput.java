package com.tinqinacademy.comments.api.models.getcommentsforroom;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class GetCommentsForRoomListOutput {
    private List<GetCommentsForRoomOutput> list;
}
