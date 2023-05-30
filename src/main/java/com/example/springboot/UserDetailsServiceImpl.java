package com.example.springboot;

import com.example.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    private final UserRepositoryCustomImpl userRepositoryCustomImpl;

    @Autowired
    public UserDetailsServiceImpl(UserRepositoryCustomImpl userRepositoryCustomImpl) {
        this.userRepositoryCustomImpl = userRepositoryCustomImpl;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepositoryCustomImpl.findByName(username);
    }

    @Transactional
    public void register(String username, String email, String password, String authority) {
        User user = new User();
        user.setName(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setAuthority(authority);
        userRepositoryCustomImpl.register(user);
    }

    public void update(User user) {
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

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(int id) {
        userRepository.deleteById(id);
    }
}