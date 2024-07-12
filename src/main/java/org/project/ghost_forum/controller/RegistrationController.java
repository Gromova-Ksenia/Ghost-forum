package org.project.ghost_forum.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.project.ghost_forum.dto.UserDto;
import org.project.ghost_forum.service.UserService;
import org.project.ghost_forum.validation.UserValidator;
import org.springframework.stereotype.Controller;
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

    @GetMapping
    public ModelAndView register(){
        return new ModelAndView("user","user",new UserDto());
    }


    @PostMapping
    @ModelAttribute("user")
    public String registerUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult, Model model){
        validator.validate(userDto, bindingResult);

        if (bindingResult.hasErrors()){
//            throw new ValidationException("Пользователь с таким username уже существует!");
            throw new ValidationException("Что-то пошло не так!");
            //return "error";
        }
        service.userRegistration(userDto);
        model.addAttribute("user", userDto);
        return "redirect:/login";
    }

//    @PostMapping
//    public ModelAndView registerUser(@RequestBody UserDto userDto, BindingResult bindingResult, Model model){
//        validator.validate(userDto, bindingResult);
//
//        if (bindingResult.hasErrors()){
//            throw new ValidationException("Что-то пошло не так!");
//        }
//        service.userRegistration(userDto);
//        //model.addAttribute("newUser",userDto);
//        return new ModelAndView().addObject("newUser", userDto);
//    }
}
