package org.project.ghost_forum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.ghost_forum.entity.Post;
import org.project.ghost_forum.entity.User;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikedPostDto {
    private UUID postId;
    private UUID userId;
    private char rate;
}
