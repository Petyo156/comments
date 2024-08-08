package com.tinqinacademy.comments.persistence.repositories;

import com.tinqinacademy.comments.persistence.entities.Comment;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, UUID> {
//    String query_find_by_roomID = """
//            select cs.comment_id,cs.content,cs.creation_date,cs.first_name,cs.last_edit_date,cs.last_name,cs.room_id
//            from comments cs
//            where cs.room_id=:roomId
//            """;
//
//    @Query(value = query_find_by_roomID, nativeQuery = true)
    List<Comment> findByRoomId(UUID roomId);

//    String query_find_by_commentID = """
//            select cs.comment_id,cs.content,cs.creation_date,cs.first_name,cs.last_edit_date,cs.last_name,cs.room_id
//            from comments cs
//            where cs.comment_id=:commentId
//            """;
//
//    @Query(value = query_find_by_commentID, nativeQuery = true)
    Optional<Comment> findByCommentId(UUID commentId);
}
