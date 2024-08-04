package com.tinqinacademy.comments.core;

import com.tinqinacademy.comments.api.models.getcommentsforroom.GetCommentsForRoomInput;
import com.tinqinacademy.comments.api.models.getcommentsforroom.GetCommentsForRoomListOutput;
import com.tinqinacademy.comments.api.models.getcommentsforroom.GetCommentsForRoomOutput;
import com.tinqinacademy.comments.api.models.leavecommentforroom.LeaveCommentForRoomInput;
import com.tinqinacademy.comments.api.models.leavecommentforroom.LeaveCommentForRoomOutput;
import com.tinqinacademy.comments.api.models.usereditowncomment.UserEditOwnCommentInput;
import com.tinqinacademy.comments.api.models.usereditowncomment.UserEditOwnCommentOutput;
import com.tinqinacademy.comments.persistence.entities.Comment;
import com.tinqinacademy.comments.persistence.repositories.CommentsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class HotelServiceImpl implements HotelService {

    private final CommentsRepository commentsRepository;

    @Autowired
    public HotelServiceImpl(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    @Override
    public GetCommentsForRoomListOutput getCommentsForRoom(GetCommentsForRoomInput input) {
        log.info("Start getCommentsForRoom input: {}", input);

        List<Comment> commentList = commentsRepository.findByRoomId(UUID.fromString(input.getRoomId()));

        if(commentList.isEmpty()){
            throw new IllegalArgumentException("Comments with room id " + input.getRoomId() + " do not exist!");
        }

        List<GetCommentsForRoomOutput> outputs = new ArrayList<>();
        for (Comment comment : commentList) {
            GetCommentsForRoomOutput output = GetCommentsForRoomOutput.builder()
                    .publishDate(comment.getCreationDate())
                    .firstName(comment.getFirstName())
                    .lastName(comment.getLastName())
                    .id(String.valueOf(comment.getCommentId()))
                    .lastEditedDate(comment.getLastEditDate())
                    .content(comment.getContent())
                    //hardcoded
                    .lastEditedBy(String.valueOf(UUID.randomUUID()))
                    .build();
            outputs.add(output);
        }

//        GetCommentsForRoomOutput output1 = GetCommentsForRoomOutput.builder()
//                .publishDate(LocalDate.parse("2030-12-12"))
//                .firstName("petar")
//                .lastName("petrov")
//                .id("555")
//                .lastEditedDate(LocalDate.parse("2030-12-12"))
//                .content("aaaa")
//                .lastEditedBy("aaaa")
//                .build();
//
//        GetCommentsForRoomOutput output2 = GetCommentsForRoomOutput.builder()
//                .publishDate(LocalDate.parse("2030-12-12"))
//                .firstName("petar")
//                .lastName("petrov")
//                .id("555")
//                .lastEditedDate(LocalDate.parse("2030-12-12"))
//                .content("aaaa")
//                .lastEditedBy("aaaa")
//                .build();

        GetCommentsForRoomListOutput finalOutput = GetCommentsForRoomListOutput.builder()
                .list(outputs)
                .build();

        log.info("End getCommentsForRoom output: {}", finalOutput);
        return finalOutput;
    }

    @Override
    public LeaveCommentForRoomOutput leaveCommentForRoom(LeaveCommentForRoomInput input) {
        log.info("Start leaveCommentForRoom input: {}", input);

        List<Comment> commentList = commentsRepository.findByRoomId(UUID.fromString(input.getRoomId()));
        if (commentList.isEmpty()) {
            throw new IllegalArgumentException("Comment with Room ID " + input.getRoomId() + " doesn't exist!");
        }

        Comment comment = Comment.builder()
                .roomId(UUID.fromString(input.getRoomId()))
                .creationDate(LocalDate.now())
                .lastEditDate(LocalDate.now())
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .content(input.getContent())
                .build();

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

        List<Comment> commentList = commentsRepository.findByCommentId(UUID.fromString(input.getCommentId()));
        if (commentList.isEmpty()) {
            throw new IllegalArgumentException("Comment with ID " + input.getCommentId() + " doesn't exist!");
        }

        Comment comment = commentList.getFirst();

        comment.setContent(input.getContent());
        comment.setLastEditDate(LocalDate.now());

        commentsRepository.save(comment);

        UserEditOwnCommentOutput output = UserEditOwnCommentOutput.builder()
                .id("hello!")
                .build();

        log.info("End userCanEditOwnComment output: {}", output);
        return output;
    }


}
