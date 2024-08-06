package com.tinqinacademy.comments.restexport;

import com.tinqinacademy.comments.api.apimapping.RestApiMapping;
import com.tinqinacademy.comments.api.operations.hotel.getcommentsforroom.GetCommentsForRoomOutput;
import com.tinqinacademy.comments.api.operations.hotel.leavecommentforroom.LeaveCommentForRoomInput;
import com.tinqinacademy.comments.api.operations.hotel.leavecommentforroom.LeaveCommentForRoomOutput;
import com.tinqinacademy.comments.api.operations.hotel.usereditowncomment.UserEditOwnCommentInput;
import com.tinqinacademy.comments.api.operations.hotel.usereditowncomment.UserEditOwnCommentOutput;
import com.tinqinacademy.comments.api.operations.system.admindeleteanycomment.AdminDeleteAnyCommentInput;
import com.tinqinacademy.comments.api.operations.system.admindeleteanycomment.AdminDeleteAnyCommentOutput;
import com.tinqinacademy.comments.api.operations.system.admineditanycomment.AdminEditAnyCommentInput;
import com.tinqinacademy.comments.api.operations.system.admineditanycomment.AdminEditAnyCommentOutput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "commentClient", url = "http://localhost:8081")
public interface CommentClient {
    //hotelcontroller
    @GetMapping(RestApiMapping.GET_PATH)
    ResponseEntity<?> getRoomComments(
            @PathVariable("roomId") String roomId);

    @PostMapping(RestApiMapping.POST_PATH)
    ResponseEntity<?> leaveCommentForRoom(
            @RequestBody LeaveCommentForRoomInput input,
            @PathVariable("roomId") String roomId);

    @PatchMapping(RestApiMapping.PATCH_PATH)
    ResponseEntity<?> userEditOwnComment(
            @RequestBody UserEditOwnCommentInput input,
            @PathVariable("commentId") String commentId);

    //systemcontroller
    @PutMapping(RestApiMapping.PUT_PATH)
    ResponseEntity<?> adminEditAnyComment(
            @RequestBody AdminEditAnyCommentInput input,
            @PathVariable("commentId") String commentId);

    @DeleteMapping(RestApiMapping.DELETE_PATH)
    ResponseEntity<?> adminDeleteAnyComment(
            @RequestBody AdminDeleteAnyCommentInput input,
            @PathVariable("commentId") String commentId);
}