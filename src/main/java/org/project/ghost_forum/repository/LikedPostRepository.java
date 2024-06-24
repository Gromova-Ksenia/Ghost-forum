package org.project.ghost_forum.repository;

import org.project.ghost_forum.entity.LikedPost;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LikedPostRepository extends JpaRepository<LikedPost, UUID>{

    @Query("FROM LikedPost WHERE pKey.post.id = :postId AND pKey.user.id = :userId")
    Optional<LikedPost> findByUserIdAndPostId(UUID userId, UUID postId);
}
