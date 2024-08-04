package com.tinqinacademy.comments.core.services;

import com.tinqinacademy.comments.api.operations.hotel.getcommentsforroom.GetCommentsForRoomInput;
import com.tinqinacademy.comments.api.operations.hotel.getcommentsforroom.GetCommentsForRoomListOutput;
import com.tinqinacademy.comments.api.operations.hotel.getcommentsforroom.GetCommentsForRoomOutput;
import com.tinqinacademy.comments.api.operations.hotel.leavecommentforroom.LeaveCommentForRoomInput;
import com.tinqinacademy.comments.api.operations.hotel.leavecommentforroom.LeaveCommentForRoomOutput;
import com.tinqinacademy.comments.api.operations.hotel.usereditowncomment.UserEditOwnCommentInput;
import com.tinqinacademy.comments.api.operations.hotel.usereditowncomment.UserEditOwnCommentOutput;
import com.tinqinacademy.comments.core.converters.LeaveCommentForRoomOutputConverter;
import com.tinqinacademy.comments.persistence.entities.Comment;
import com.tinqinacademy.comments.persistence.repositories.CommentsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HotelServiceImpl implements HotelService {

    private final CommentsRepository commentsRepository;
    private final LeaveCommentForRoomOutputConverter leaveCommentForRoomOutputConverter;

    @Autowired
    public HotelServiceImpl(CommentsRepository commentsRepository, LeaveCommentForRoomOutputConverter leaveCommentForRoomOutputConverter) {
        this.commentsRepository = commentsRepository;
        this.leaveCommentForRoomOutputConverter = leaveCommentForRoomOutputConverter;
    }

    @Override
    public GetCommentsForRoomListOutput getCommentsForRoom(GetCommentsForRoomInput input) {
        log.info("Start getCommentsForRoom input: {}", input);

        List<Comment> commentList = commentsRepository.findByRoomId(UUID.fromString(input.getRoomId()));

        throwIfCommentsForRoomDontExist(commentList);

        List<GetCommentsForRoomOutput> outputs = commentList.stream()
                .map(comment -> GetCommentsForRoomOutput.builder()
                        .publishDate(comment.getCreationDate())
                        .firstName(comment.getFirstName())
                        .lastName(comment.getLastName())
                        .id(String.valueOf(comment.getCommentId()))
                        .lastEditedDate(comment.getLastEditDate())
                        .content(comment.getContent())
                        //hardcoded - have to merge hotel and comments first
                        .lastEditedBy(String.valueOf(UUID.randomUUID()))
                        .build())
                .collect(Collectors.toList());

        GetCommentsForRoomListOutput finalOutput = GetCommentsForRoomListOutput.builder()
                .list(outputs)
                .build();

        log.info("End getCommentsForRoom output: {}", finalOutput);
        return finalOutput;
    }

    private static void throwIfCommentsForRoomDontExist(List<Comment> commentList) {
        if (commentList.isEmpty()) {
            throw new IllegalArgumentException("Comments for given RoomID do not exist!");
        }
    }

    @Override
    public LeaveCommentForRoomOutput leaveCommentForRoom(LeaveCommentForRoomInput input) {
        log.info("Start leaveCommentForRoom input: {}", input);

        List<Comment> commentList = commentsRepository.findByRoomId(UUID.fromString(input.getRoomId()));

        throwIfCommentsForRoomDontExist(commentList);

        Comment comment = leaveCommentForRoomOutputConverter.convert(input).build();

        commentsRepository.save(comment);

        LeaveCommentForRoomOutput output = LeaveCommentForRoomOutput.builder()
                .id(String.valueOf(comment.getCommentId()))
                .build();

        log.info("End leaveCommentForRoom output: {}", output);
        return output;
    }

    @Override
    public UserEditOwnCommentOutput userCanEditOwnComment(UserEditOwnCommentInput input) {
        log.info("Start userCanEditOwnComment input: {}", input);

        Optional<Comment> commentOptional = commentsRepository.findByCommentId(UUID.fromString(input.getCommentId()));
        throwIfCommentDoesntExist(commentOptional);

        Comment comment = commentOptional.get().toBuilder()
                .content(input.getContent())
                .lastEditDate(LocalDate.now())
                .build();

        commentsRepository.save(comment);

        UserEditOwnCommentOutput output = UserEditOwnCommentOutput.builder()
                .id(String.valueOf(comment.getCommentId()))
                .build();

        log.info("End userCanEditOwnComment output: {}", output);
        return output;
    }

    private static void throwIfCommentDoesntExist(Optional<Comment> comment) {
        if (comment.isEmpty()) {
            throw new IllegalArgumentException("Comment with given ID doesn't exist!");
        }
    }


}
