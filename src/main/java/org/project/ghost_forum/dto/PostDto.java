package org.project.ghost_forum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {
    private UUID id;
    private UUID authorId;
    private String authorUsername;
    private String title;
    private String body;
    private LocalDateTime creationTime;
    private int rating = 0;
    private Set<CommentDto> comments;
    private Set<TagDto> tags;

    public void increaseRating(){
        this.rating+=1;
    }

    public void decreaseRating(){
        this.rating-=1;
    }
}
