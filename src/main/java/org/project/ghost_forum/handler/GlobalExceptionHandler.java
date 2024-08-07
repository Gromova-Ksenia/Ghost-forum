package org.project.ghost_forum.handler;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class) //Сущность не найдена - возвращаем статус
    public ResponseEntity<ExceptionMessageObject> handleEntityNotFoundException(EntityNotFoundException exception) {
        ExceptionMessageObject message = ExceptionMessageObject.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .dateTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class) //Что-то пошло не по плану, тоже выдаём ошибку
    public ResponseEntity<ExceptionMessageObject> handleConstraintViolationException(ValidationException exception) {
        ExceptionMessageObject message = ExceptionMessageObject.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .dateTime(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
