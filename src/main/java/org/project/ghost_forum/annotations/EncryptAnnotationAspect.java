package org.project.ghost_forum.annotations;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.project.ghost_forum.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Aspect
@RequiredArgsConstructor
@Component
public class EncryptAnnotationAspect {
    @Autowired
    private final PasswordEncoder passwordEncoder;

    //Если Encrypt и в аргументах дто - будет шифрование
    @Pointcut("@annotation(Encrypt) && args(userDto,..)")
    public void callEncryptableMethod(UserDto userDto) {}

    //Оповещалка до шифрования
    @Before(value = "callEncryptableMethod(userDto)", argNames = "userDto")
    public void beforeCallEncryptableMethod(UserDto userDto) {
        System.out.println("Пароль будет зашифрован!");
        System.out.println(userDto);

        String password = userDto.getPassword();

        String encodedPassword = passwordEncoder.encode(password);

        userDto.setPassword(encodedPassword);
    }

    //Оповещалка после
    @After(value = "callEncryptableMethod(userDto)", argNames = "userDto")
    public void afterCallEncryptableMethod(UserDto userDto) {
        System.out.println("Сущность юзера сохранена в БД, пароль зашифрован");
        System.out.println(userDto);
    }

}
