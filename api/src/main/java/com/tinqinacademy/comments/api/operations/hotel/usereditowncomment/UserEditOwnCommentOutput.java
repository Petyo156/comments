package com.tinqinacademy.comments.api.operations.hotel.usereditowncomment;

import com.tinqinacademy.comments.api.base.OperationOutput;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserEditOwnCommentOutput implements OperationOutput {
    private String id;
}
