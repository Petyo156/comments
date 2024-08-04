package com.tinqinacademy.comments.core.services;

import com.tinqinacademy.comments.api.operations.system.admindeleteanycomment.AdminDeleteAnyCommentInput;
import com.tinqinacademy.comments.api.operations.system.admindeleteanycomment.AdminDeleteAnyCommentOutput;
import com.tinqinacademy.comments.api.operations.system.admineditanycomment.AdminEditAnyCommentInput;
import com.tinqinacademy.comments.api.operations.system.admineditanycomment.AdminEditAnyCommentOutput;
import com.tinqinacademy.comments.persistence.entities.Comment;
import com.tinqinacademy.comments.persistence.repositories.CommentsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

        Optional<Comment> commentOptional = commentsRepository.findByCommentId(UUID.fromString(input.getCommentId()));
        throwIfCommentDoesntExist(commentOptional);

        Comment comment = commentOptional.get().toBuilder()
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .content(input.getContent())
                //.roomNo?
                .build();

        commentsRepository.save(comment);

        AdminEditAnyCommentOutput output = AdminEditAnyCommentOutput.builder()
                .id(input.getCommentId())
                .build();

        log.info("End adminEditAnyComment output: {}", output);
        return output;
    }

    private static void throwIfCommentDoesntExist(Optional<Comment> commentOptional) {
        if (commentOptional.isEmpty()) {
            throw new IllegalArgumentException("Comment with given ID doesn't exist!");
        }
    }

    @Override
    public AdminDeleteAnyCommentOutput adminDeleteAnyComment(AdminDeleteAnyCommentInput input) {
        log.info("Start adminDeleteAnyComment input: {}", input);

        Optional<Comment> commentOptional = commentsRepository.findByCommentId(UUID.fromString(input.getCommentId()));

        commentsRepository.delete(commentOptional.get());

        AdminDeleteAnyCommentOutput output = AdminDeleteAnyCommentOutput.builder()
                .build();

        log.info("End adminEditAnyComment output: {}", output);
        return output;
    }
}
