package org.project.ghost_forum.service;

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
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;
    private final UserRepository repository;
    private final RoleService roleService;

    @Transactional
    @Encrypt  //Регистрация
    public UserDto userRegistration(UserDto userDto) {

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
    public Boolean unbanUser(UUID id) {
        return repository.findById(id)
                .map(user -> {
                    user.setIsBanned(false);
                    repository.save(user);
                    return Boolean.TRUE;
                })
                .orElse(Boolean.FALSE);
    }

    //Проверка на наличие роли администратора
    public boolean hasAdminAuthority(UUID id) {
        repository.findById(id).map(user -> {
            if (user.getRoles().contains(roleService.findRoleByName(RoleType.ROLE_ADMIN))) return true;
            else return false;
        }).orElse(false);
        return false;
    }

    public User getUserById(UUID id) {
        return repository.findById(id).orElseThrow();
    }

    //Ием по юзернейму без ошпинала
    public User getUserByUsername(String username) {
        return repository.findByUsername(username).orElseThrow();
    }

    //Разницы, кроме Optional нет
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }
}
