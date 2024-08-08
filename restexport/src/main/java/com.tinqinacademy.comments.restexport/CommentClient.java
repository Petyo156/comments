package com.tinqinacademy.comments.restexport;

import com.tinqinacademy.comments.api.apimapping.RestApiMappingComments;
import com.tinqinacademy.comments.api.operations.hotel.leavecommentforroom.LeaveCommentForRoomInput;
import com.tinqinacademy.comments.api.operations.hotel.usereditowncomment.UserEditOwnCommentInput;
import com.tinqinacademy.comments.api.operations.system.admindeleteanycomment.AdminDeleteAnyCommentInput;
import com.tinqinacademy.comments.api.operations.system.admineditanycomment.AdminEditAnyCommentInput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "commentClient", url = "http://localhost:8081")
public interface CommentClient {
    //hotelcontroller
    @GetMapping(RestApiMappingComments.GET_PATH)
    ResponseEntity<?> getRoomComments(
            @PathVariable("roomId") String roomId);

    @PostMapping(RestApiMappingComments.POST_PATH)
    ResponseEntity<?> leaveCommentForRoom(
            @RequestBody LeaveCommentForRoomInput input,
            @PathVariable("roomId") String roomId);

    @PatchMapping(RestApiMappingComments.PATCH_PATH)
    ResponseEntity<?> userEditOwnComment(
            @RequestBody UserEditOwnCommentInput input,
            @PathVariable("commentId") String commentId);

    //systemcontroller
    @PutMapping(RestApiMappingComments.PUT_PATH)
    ResponseEntity<?> adminEditAnyComment(
            @RequestBody AdminEditAnyCommentInput input,
            @PathVariable("commentId") String commentId);

    @DeleteMapping(RestApiMappingComments.DELETE_PATH)
    ResponseEntity<?> adminDeleteAnyComment(
            @RequestBody AdminDeleteAnyCommentInput input,
            @PathVariable("commentId") String commentId);
}