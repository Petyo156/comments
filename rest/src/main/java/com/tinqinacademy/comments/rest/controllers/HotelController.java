package com.tinqinacademy.comments.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.comments.api.apimapping.RestApiMappingComments;
import com.tinqinacademy.comments.api.operations.hotel.getcommentsforroom.GetCommentsForRoomInput;
import com.tinqinacademy.comments.api.operations.hotel.leavecommentforroom.LeaveCommentForRoomInput;
import com.tinqinacademy.comments.api.operations.hotel.usereditowncomment.UserEditOwnCommentInput;
import com.tinqinacademy.comments.core.processors.hotel.GetCommentsForRoomOperationProcessor;
import com.tinqinacademy.comments.core.processors.hotel.LeaveCommentForRoomOperationProcessor;
import com.tinqinacademy.comments.core.processors.hotel.UserEditOwnCommentOperationProcessor;
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

    @GetMapping(RestApiMappingComments.GET_PATH)
    public ResponseEntity<?> getRoomComments(
            @PathVariable("roomId") String roomId) {

        GetCommentsForRoomInput getCommentsForRoomInput = GetCommentsForRoomInput.builder()
                .roomId(roomId)
                .build();

        return handleOperation(getCommentsForRoomOperationProcessor.process(getCommentsForRoomInput), HttpStatus.BAD_REQUEST);
    }

    @PostMapping(RestApiMappingComments.POST_PATH)
    public ResponseEntity<?> leaveCommentForRoom(
            @Valid @RequestBody LeaveCommentForRoomInput input,
            @PathVariable("roomId") String roomId) {

        LeaveCommentForRoomInput leaveCommentForRoomInput = input.toBuilder()
                .roomId(roomId)
                .build();

        return handleOperation(leaveCommentForRoomOperationProcessor.process(leaveCommentForRoomInput), HttpStatus.BAD_REQUEST);
    }

    @PatchMapping(RestApiMappingComments.PATCH_PATH)
    public ResponseEntity<?> userEditOwnComment(
            @Valid @RequestBody UserEditOwnCommentInput input,
            @PathVariable("commentId") String commentId) {

        UserEditOwnCommentInput userEditOwnCommentInput = input.toBuilder()
                .commentId(commentId)
                .build();

        return handleOperation(userEditOwnCommentOperationProcessor.process(userEditOwnCommentInput), HttpStatus.BAD_REQUEST);
    }

}
