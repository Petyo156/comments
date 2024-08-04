package com.tinqinacademy.comments.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.comments.api.operations.hotel.getcommentsforroom.GetCommentsForRoomInput;
import com.tinqinacademy.comments.api.operations.hotel.getcommentsforroom.GetCommentsForRoomListOutput;
import com.tinqinacademy.comments.api.operations.hotel.leavecommentforroom.LeaveCommentForRoomInput;
import com.tinqinacademy.comments.api.operations.hotel.leavecommentforroom.LeaveCommentForRoomOutput;
import com.tinqinacademy.comments.api.operations.hotel.usereditowncomment.UserEditOwnCommentInput;
import com.tinqinacademy.comments.api.operations.hotel.usereditowncomment.UserEditOwnCommentOutput;
import com.tinqinacademy.comments.core.processors.hotel.GetCommentsForRoomOperationProcessor;
import com.tinqinacademy.comments.core.processors.hotel.LeaveCommentForRoomOperationProcessor;
import com.tinqinacademy.comments.core.processors.hotel.UserEditOwnCommentOperationProcessor;
import com.tinqinacademy.comments.core.services.HotelService;
import com.tinqinacademy.comments.rest.config.RestApiMapping;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HotelController extends BaseController {
    //only in post put patch POJOS are needed validations
    //only in get is used request param(anywhere else - request body)

    private final ObjectMapper objectMapper;

    private final GetCommentsForRoomOperationProcessor getCommentsForRoomOperationProcessor;
    private final LeaveCommentForRoomOperationProcessor leaveCommentForRoomOperationProcessor;
    private final UserEditOwnCommentOperationProcessor userEditOwnCommentOperationProcessor;

    @Autowired
    public HotelController(ObjectMapper objectMapper, GetCommentsForRoomOperationProcessor getCommentsForRoomOperationProcessor,
                           LeaveCommentForRoomOperationProcessor leaveCommentForRoomOperationProcessor, UserEditOwnCommentOperationProcessor userEditOwnCommentOperationProcessor) {
        this.objectMapper = objectMapper;
        this.getCommentsForRoomOperationProcessor = getCommentsForRoomOperationProcessor;
        this.leaveCommentForRoomOperationProcessor = leaveCommentForRoomOperationProcessor;
        this.userEditOwnCommentOperationProcessor = userEditOwnCommentOperationProcessor;
    }

    @GetMapping(RestApiMapping.GET_PATH)
    public ResponseEntity<?> getRoomComments(
            @PathVariable("roomId") String roomId) {

        GetCommentsForRoomInput input1 = GetCommentsForRoomInput.builder()
                .roomId(roomId)
                .build();

        //return new ResponseEntity<>(hotelService.getCommentsForRoom(input1), HttpStatus.OK);
        return handleOperation(getCommentsForRoomOperationProcessor.process(input1), HttpStatus.BAD_REQUEST);
    }

    @PostMapping(RestApiMapping.POST_PATH)
    public ResponseEntity<?> leaveCommentForRoom(
            @Valid @RequestBody LeaveCommentForRoomInput input,
            @PathVariable("roomId") String roomId) {

        LeaveCommentForRoomInput input1 = input.toBuilder()
                .roomId(roomId)
                .build();

        //return new ResponseEntity<>(hotelService.leaveCommentForRoom(input1), HttpStatus.OK);
        return handleOperation(leaveCommentForRoomOperationProcessor.process(input1), HttpStatus.BAD_REQUEST);
    }

    @PatchMapping(RestApiMapping.PATCH_PATH)
    public ResponseEntity<?> userEditOwnComment(
            @Valid @RequestBody UserEditOwnCommentInput input,
            @PathVariable("commentId") String commentId) {

        UserEditOwnCommentInput input1 = input.toBuilder()
                .commentId(commentId)
                .build();

        //return new ResponseEntity<>(hotelService.userCanEditOwnComment(input1), HttpStatus.OK);
        return handleOperation(userEditOwnCommentOperationProcessor.process(input1), HttpStatus.BAD_REQUEST);
    }

}
