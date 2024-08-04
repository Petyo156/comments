package com.tinqinacademy.comments.core.converters;

import com.tinqinacademy.comments.api.operations.hotel.leavecommentforroom.LeaveCommentForRoomInput;
import com.tinqinacademy.comments.api.operations.hotel.leavecommentforroom.LeaveCommentForRoomOutput;
import com.tinqinacademy.comments.persistence.entities.Comment;
import jakarta.persistence.Entity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class LeaveCommentForRoomOutputConverter implements Converter<LeaveCommentForRoomInput, Comment.CommentBuilder> {
    @Override
    public Comment.CommentBuilder convert(LeaveCommentForRoomInput input) {
        return Comment.builder()
                .roomId(UUID.fromString(input.getRoomId()))
                .creationDate(LocalDate.now())
                .lastEditDate(LocalDate.now())
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .content(input.getContent());
    }
}
