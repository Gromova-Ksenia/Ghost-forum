package org.project.ghost_forum.controller;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.project.ghost_forum.dto.UserDto;
import org.project.ghost_forum.service.UserService;
import org.project.ghost_forum.validation.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/registration")
public class RegistrationController {

    private final UserService service;
    private final UserValidator validator;

    @GetMapping
    public String register(Model model){
        model.addAttribute("user", new UserDto());
        return "user";
    }


    @PostMapping
    @ModelAttribute("user")
    public String registerUser(@RequestBody UserDto userDto, BindingResult bindingResult){
        validator.validate(userDto, bindingResult);

        if (bindingResult.hasErrors()){
//            throw new ValidationException("Пользователь с таким username уже существует!");
            throw new ValidationException("Что-то пошло не так!");
        }
        service.userRegistration(userDto);
        return "redirect:/login";
    }
}
