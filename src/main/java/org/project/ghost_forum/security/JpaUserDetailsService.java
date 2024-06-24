package org.project.ghost_forum.security;

import lombok.RequiredArgsConstructor;
import org.project.ghost_forum.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {
    private final UserRepository repository;

    @Override //Ищем по юзернейму
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return repository.findByUsername(username).map(AuthUser::new).orElseThrow(() ->
                new UsernameNotFoundException("Пользователь с username " + username + " не найден!"));
    }
}
