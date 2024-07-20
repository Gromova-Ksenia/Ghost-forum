package org.project.ghost_forum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikedPostDto implements Serializable {
    private UUID postId;
    private UUID userId;
    private String rate;
}
