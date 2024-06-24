package org.project.ghost_forum.repository;

import org.project.ghost_forum.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

    @Query("FROM Comment c WHERE c.post.id = :postId")
    List<Comment> findAllByPost(UUID postId);
}
