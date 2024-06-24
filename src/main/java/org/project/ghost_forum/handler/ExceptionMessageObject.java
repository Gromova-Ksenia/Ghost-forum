package org.project.ghost_forum.handler;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class ExceptionMessageObject {
    private HttpStatus status;

    private String message;

    private LocalDateTime dateTime;
}
