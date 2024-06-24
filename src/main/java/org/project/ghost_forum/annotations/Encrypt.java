package org.project.ghost_forum.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) //Помечаем потом шифровку
@Retention(RetentionPolicy.RUNTIME)
public @interface Encrypt {
}
