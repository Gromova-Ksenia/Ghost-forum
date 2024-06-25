package org.project.ghost_forum.optional;

import org.project.ghost_forum.annotations.Encrypt;
import org.project.ghost_forum.entity.Post;
import org.project.ghost_forum.entity.Role;
import org.project.ghost_forum.entity.Tag;
import org.project.ghost_forum.entity.User;
import org.project.ghost_forum.enums.RoleType;
import org.project.ghost_forum.enums.TagName;
import org.project.ghost_forum.repository.PostRepository;
import org.project.ghost_forum.repository.RoleRepository;
import org.project.ghost_forum.repository.TagRepository;
import org.project.ghost_forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Component
public class DataLoader implements ApplicationRunner {
    private TagRepository tagRepository;
    private RoleRepository roleRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(TagRepository tagRepository, RoleRepository roleRepository,
                      PostRepository postRepository, UserRepository userRepository){
        this.tagRepository = tagRepository;
        this.roleRepository = roleRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public void run(ApplicationArguments args){
        tagRepository.save(new Tag(UUID.randomUUID(), TagName.АРТ));
        tagRepository.save(new Tag(UUID.randomUUID(), TagName.ХЕДКАНОН));
        tagRepository.save(new Tag(UUID.randomUUID(), TagName.КОСПЛЕЙ));
        tagRepository.save(new Tag(UUID.randomUUID(), TagName.КАВЕР));
        tagRepository.save(new Tag(UUID.randomUUID(), TagName.ФАНФИК));
        tagRepository.save(new Tag(UUID.randomUUID(), TagName.ХЕНДМЕЙД));
        tagRepository.save(new Tag(UUID.randomUUID(), TagName.ВИДЕО));
        tagRepository.save(new Tag(UUID.randomUUID(), TagName.NSFW));
        tagRepository.save(new Tag(UUID.randomUUID(), TagName.ДРУГОЕ));

        Role userRole = new Role(UUID.randomUUID(), RoleType.ROLE_USER);
        Role adminRole = new Role(UUID.randomUUID(), RoleType.ROLE_ADMIN);
        userRole = roleRepository.save(userRole);
        adminRole = roleRepository.save(adminRole);


        User testUser = User.builder()
                .id(UUID.randomUUID())
                .username("TestUser")
                .password(passwordEncoder.encode("randomthing"))
                .registrationDate(LocalDate.now())
                .roles(Set.of(userRole, adminRole))
                .build();

        testUser = userRepository.save(testUser);

        Post testPost = Post.builder()
                .id(UUID.randomUUID())
                .author(testUser)
                .title("Это пробный пост")
                .body("Тут ничего интересного, просто проверка")
                .creationTime(LocalDateTime.now())
                .build();

        postRepository.save(testPost);
    }

}
