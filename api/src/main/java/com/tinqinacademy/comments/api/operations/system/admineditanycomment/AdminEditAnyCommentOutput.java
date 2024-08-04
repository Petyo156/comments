package com.tinqinacademy.comments.api.operations.system.admineditanycomment;

import com.tinqinacademy.comments.api.base.OperationOutput;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AdminEditAnyCommentOutput implements OperationOutput {
    private String id;
}
