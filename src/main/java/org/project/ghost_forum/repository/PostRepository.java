package org.project.ghost_forum.repository;

import org.project.ghost_forum.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    @Query("FROM Post p WHERE p.author.id = :authorId")
    List<Post> findAllByAuthor(UUID authorId);

}
