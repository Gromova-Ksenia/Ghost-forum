package org.project.ghost_forum.repository;

import org.project.ghost_forum.entity.Role;
import org.project.ghost_forum.entity.Tag;
import org.project.ghost_forum.enums.TagName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {

    @Query("FROM Tag t WHERE t.name = :name")
    Role findByName(TagName name);

}
