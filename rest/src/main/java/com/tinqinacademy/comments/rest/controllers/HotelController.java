package com.tinqinacademy.comments.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.comments.api.models.getcommentsforroom.GetCommentsForRoomInput;
import com.tinqinacademy.comments.api.models.getcommentsforroom.GetCommentsForRoomListOutput;
import com.tinqinacademy.comments.api.models.leavecommentforroom.LeaveCommentForRoomInput;
import com.tinqinacademy.comments.api.models.leavecommentforroom.LeaveCommentForRoomOutput;
import com.tinqinacademy.comments.api.models.usereditowncomment.UserEditOwnCommentInput;
import com.tinqinacademy.comments.api.models.usereditowncomment.UserEditOwnCommentOutput;
import com.tinqinacademy.comments.core.HotelService;
import com.tinqinacademy.comments.rest.config.RestApiMapping;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HotelController {
    //only in post put patch POJOS are needed validations
    //only in get is used request param(anywhere else - request body)

    private HotelService hotelService;
    private ObjectMapper objectMapper;

    @Autowired
    public HotelController(HotelService hotelService, ObjectMapper objectMapper) {
        this.hotelService = hotelService;
        this.objectMapper = objectMapper;
    }

    @GetMapping(RestApiMapping.GET_PATH)
    public ResponseEntity<GetCommentsForRoomListOutput> getRoomComments(
            @PathVariable("roomId") String roomId) {

        GetCommentsForRoomInput input1 = GetCommentsForRoomInput.builder()
                .roomId(roomId)
                .build();

        return new ResponseEntity<>(hotelService.getCommentsForRoom(input1), HttpStatus.OK);
    }

    @PostMapping(RestApiMapping.POST_PATH)
    public ResponseEntity<LeaveCommentForRoomOutput> leaveCommentForRoom(
            @Valid @RequestBody LeaveCommentForRoomInput input,
            @PathVariable("roomId") String roomId) {

        LeaveCommentForRoomInput input1 = input.toBuilder()
                .roomId(roomId)
                .build();

        return new ResponseEntity<>(hotelService.leaveCommentForRoom(input1), HttpStatus.OK);
    }

    @PatchMapping(RestApiMapping.PATCH_PATH)
    public ResponseEntity<UserEditOwnCommentOutput> userEditOwnComment(
            @Valid @RequestBody UserEditOwnCommentInput input,
            @PathVariable("commentId") String commentId) {

        UserEditOwnCommentInput input1 = input.toBuilder()
                .commentId(commentId)
                .build();

        return new ResponseEntity<>(hotelService.userCanEditOwnComment(input1), HttpStatus.OK);
    }

}
