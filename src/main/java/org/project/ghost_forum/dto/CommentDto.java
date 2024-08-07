package org.project.ghost_forum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto implements Serializable {
    private UUID id;
    private UUID postId;
    private UUID userId;
    private String userUsername;
    private LocalDateTime creationTime;
    private String body;
}
