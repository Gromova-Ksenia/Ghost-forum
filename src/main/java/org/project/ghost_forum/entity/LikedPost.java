package org.project.ghost_forum.entity;
import org.antlr.v4.runtime.misc.NotNull;
import org.project.ghost_forum.entity.primary_keys.LikedPostsPK;
//import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "liked_posts")
public class LikedPost {

    @EmbeddedId
    private LikedPostsPK pKey;

    @Column(name = "rate")
    @NotNull
    private char rate;
}

