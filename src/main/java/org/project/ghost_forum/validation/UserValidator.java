package org.project.ghost_forum.validation;

import lombok.RequiredArgsConstructor;
import org.project.ghost_forum.dto.UserDto;
import org.project.ghost_forum.entity.User;
import org.project.ghost_forum.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {
    private final UserService userService;
    @Override
    public boolean supports(Class<?> clazz){
        return UserDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors){
        UserDto targetUser = (UserDto) target;

        boolean exists = userService.findByUsername(targetUser.getUsername()).isPresent();

        if (exists){
            errors.rejectValue("username", "","Пользователь с таким username существует.");
        }
    }
}
