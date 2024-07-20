package org.project.ghost_forum.exception;

public class LoginExistException extends RuntimeException {

    public LoginExistException(String message) {
        super(message);
    }

}
