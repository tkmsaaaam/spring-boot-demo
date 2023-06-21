package com.example.springboot.service;

import com.example.springboot.Entity.User;
import com.example.springboot.model.UserDetailsImpl;
import com.example.springboot.repository.UserCustomRepository;
import com.example.springboot.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsCustomService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final UserCustomRepository userCustomRepository;

    private final String ROLE_USER = "ROLE_USER";

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userCustomRepository.findByName(username);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (user.isPresent()) {
            authorities.add(new SimpleGrantedAuthority(user.get().getAuthority()));
            return UserDetailsImpl.builder()
                    .username(user.get().getName())
                    .password(user.get().getPassword())
                    .authorities(authorities)
                    .build();
        } else {
            return new UserDetailsImpl(null, null, authorities);
        }
    }

    @Transactional
    public void register(String username, String email, String password, String authority) {
        User user = User.builder()
                .name(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .authority(authority)
                .build();
        userCustomRepository.register(user);
    }

    public void register(User user) {
        user.setAuthority(ROLE_USER);
        userCustomRepository.register(user);
    }

    public void update(User user) {
        user.setAuthority(ROLE_USER);
        userCustomRepository.update(user);
    }

    public boolean isExistUser(String username) {
        return userCustomRepository.isExisted(username) != 0;
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