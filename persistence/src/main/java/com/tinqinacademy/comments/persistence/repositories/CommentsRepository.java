package com.tinqinacademy.comments.persistence.repositories;

import com.tinqinacademy.comments.persistence.entities.Comment;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findByRoomId(UUID roomId);
    Optional<Comment> findByCommentId(UUID commentId);
}
