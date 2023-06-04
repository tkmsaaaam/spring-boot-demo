package com.example.springboot;

import com.example.springboot.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final UserRepositoryCustomImpl userRepositoryCustomImpl;

    private final String ROLE_USER = "ROLE_USER";

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepositoryCustomImpl.findByName(username);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getAuthority()));
        return UserDetailsImpl.builder()
                .username(user.getName())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }

    @Transactional
    public void register(String username, String email, String password, String authority) {
        User user = User.builder()
                .name(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .authority(authority)
                .build();
        userRepositoryCustomImpl.register(user);
    }

    public void register(User user) {
        user.setAuthority(ROLE_USER);
        userRepositoryCustomImpl.register(user);
    }

    public void update(User user) {
        user.setAuthority(ROLE_USER);
        userRepositoryCustomImpl.update(user);
    }

    public boolean isExistUser(String username) {
        return userRepositoryCustomImpl.isExisted(username);
    }

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    public void deleteById(int id) {
        userRepository.deleteById(id);
    }
}