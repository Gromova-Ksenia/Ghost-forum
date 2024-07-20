package org.project.ghost_forum.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.project.ghost_forum.dto.UserDto;
import org.project.ghost_forum.exception.LoginExistException;
import org.project.ghost_forum.service.UserService;
import org.project.ghost_forum.validation.UserValidator;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/registration")
public class RegistrationController {

    private final UserService service;
    private final UserValidator validator;

    @PostMapping //Регистрация
    public String registerUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
        validator.validate(userDto, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new LoginExistException("Пользователь с таким username уже существует!");
        }
        service.userRegistration(userDto);
        return "redirect:/login";
    }
}

