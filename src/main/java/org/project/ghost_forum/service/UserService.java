package org.project.ghost_forum.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.project.ghost_forum.annotations.Encrypt;
import org.project.ghost_forum.dto.RoleDto;
import org.project.ghost_forum.dto.UserDto;
import org.project.ghost_forum.entity.Role;
import org.project.ghost_forum.entity.User;
import org.project.ghost_forum.enums.RoleType;
import org.project.ghost_forum.mapper.UserMapper;
import org.project.ghost_forum.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;
    private final UserRepository repository;
    private final RoleService roleService;
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Encrypt //Вход за юзера?
    public UserDto addUser(UserDto userDto) {
        List<UUID> roleIds = userDto.getRoles().stream().map(RoleDto::getId).collect(Collectors.toList());

        Set<Role> roles = roleService.getRoles(roleIds);

        User entity = mapper.toEntity(userDto);
        entity.setRoles(roles);

        User savedEntity = repository.save(entity);

        UserDto dto = mapper.toDto(savedEntity);
        dto.setRoles(roles.stream()
                .map(role -> RoleDto.builder()
                    .id(role.getId())
                    .role(role.getRoleType().name())
                    .build())
                .collect(Collectors.toSet()));

        return dto;
    }

    @Transactional
    @Encrypt  //Регистрация
    public UserDto userRegistration(UserDto userDto){

        Role userRole = roleService.findRoleByName(RoleType.ROLE_USER);

        User newUser = User.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .registrationDate(LocalDate.now())
                .roles(Set.of(userRole)).build();

        User savedUser = repository.save(newUser);

        UserDto dto = mapper.toDto(savedUser);
        dto.setRoles(Set.of(RoleDto.builder()
                .id(userRole.getId())
                .role(userRole.getRoleType().name())
                .build()));

        return dto;
    }

    //Я ничё не понял
    public UserDto getCurrent() {//Запрашиваем нынешнего пользователя
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {//Если найден,апреобразуем и получаем юзернейм
            UserDetails userDetails = (UserDetails) principal;
            String username = userDetails.getUsername();

            return repository.findByUsername(username)
                    .map(mapper::toDto)
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь с указанным username не найден"));
        }

        return null;
    }

    @Transactional
    public Boolean banUser(UUID id) {
        return repository.findById(id)
                .map(user -> {
                    user.setIsBanned(true);
                    repository.save(user);
                    return Boolean.TRUE;
                })
                .orElse(Boolean.FALSE);
    }

    @Transactional
    public Boolean unbanUser(UUID id){
        return repository.findById(id)
                .map(user -> {
                    user.setIsBanned(false);
                    repository.save(user);
                    return Boolean.TRUE;
                })
                .orElse(Boolean.FALSE);
    }

    public User getUserById(UUID id){
        return repository.findById(id).orElseThrow();
    }

    //Ием по юзернейму без ошпинала
    public User getUserByUsername(String username){
        return repository.findByUsername(username).orElseThrow();
    }

    //Разницы, кроме опшинала нет, но в одном месте мне нужен ошинал
    public Optional<User> findByUsername(String username){
        return repository.findByUsername(username);
    }
}
