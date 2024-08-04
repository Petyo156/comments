package com.tinqinacademy.comments.core;

import com.tinqinacademy.comments.api.models.admindeleteanycomment.AdminDeleteAnyCommentInput;
import com.tinqinacademy.comments.api.models.admindeleteanycomment.AdminDeleteAnyCommentOutput;
import com.tinqinacademy.comments.api.models.admineditanycomment.AdminEditAnyCommentInput;
import com.tinqinacademy.comments.api.models.admineditanycomment.AdminEditAnyCommentOutput;
import com.tinqinacademy.comments.persistence.entities.Comment;
import com.tinqinacademy.comments.persistence.repositories.CommentsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class SystemServiceImpl implements SystemService {
    private final CommentsRepository commentsRepository;

    @Autowired
    public SystemServiceImpl(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    @Override
    public AdminEditAnyCommentOutput adminEditAnyComment(AdminEditAnyCommentInput input) {
        log.info("Start adminEditAnyComment input: {}", input);

        List<Comment> commentList = commentsRepository.findByCommentId(UUID.fromString(input.getCommentId()));
        if (commentList.isEmpty()) {
            throw new IllegalArgumentException("Comment with ID " + input.getCommentId() + " doesn't exist!");
        }

        Comment comment = commentList.getFirst();
        comment.setFirstName(input.getFirstName());
        comment.setLastName(input.getFirstName());
        //comment.setRoomNo(input.getRoomNo());
        comment.setContent(input.getContent());

        commentsRepository.save(comment);

        AdminEditAnyCommentOutput output = AdminEditAnyCommentOutput.builder()
                .id(input.getCommentId())
                .build();

        log.info("End adminEditAnyComment output: {}", output);
        return output;
    }

    @Override
    public AdminDeleteAnyCommentOutput adminDeleteAnyComment(AdminDeleteAnyCommentInput input) {
        log.info("Start adminDeleteAnyComment input: {}", input);

        List<Comment> commentList = commentsRepository.findByCommentId(UUID.fromString(input.getCommentId()));
        if (commentList.isEmpty()) {
            throw new IllegalArgumentException("Comment with ID " + input.getCommentId() + " doesn't exist!");
        }

        Comment comment = commentList.getFirst();

        commentsRepository.delete(comment);

        AdminDeleteAnyCommentOutput output = AdminDeleteAnyCommentOutput.builder()
                .build();

        log.info("End adminEditAnyComment output: {}", output);
        return output;
    }
}
