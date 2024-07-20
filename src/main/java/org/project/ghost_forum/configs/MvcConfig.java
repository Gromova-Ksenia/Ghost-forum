package org.project.ghost_forum.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override //Привязка контроллеров к страницам
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/registration").setViewName("registration");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/posts").setViewName("posts");
        registry.addViewController("/post").setViewName("post");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/user").setViewName("user");
        registry.addViewController("/admin").setViewName("admin");
        registry.addViewController("/error").setViewName("error");
        registry.addViewController("/my-profile").setViewName("my-profile");
        registry.addViewController("/new-post").setViewName("new-post");
        registry.addViewController("/access-denied").setViewName("access-denied");
    }
}
