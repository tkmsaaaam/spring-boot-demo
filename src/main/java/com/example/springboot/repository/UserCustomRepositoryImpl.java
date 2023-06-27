package com.example.springboot.repository;

import com.example.springboot.Entity.User;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class UserCustomRepositoryImpl implements UserCustomRepository {
    private final JdbcTemplate jdbcTemplate;

    public Integer countByName(String name) {
        String sql = "SELECT COUNT(*) FROM user WHERE name = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, name);
    }

    public Optional<User> findByName(String name) {
        String sql = "SELECT * FROM user WHERE name = ?";
        try {
            Map<String, Object> map = jdbcTemplate.queryForMap(sql, name);
            return Optional.of(User.builder()
                    .name((String) map.get("name"))
                    .email((String) map.get("email"))
                    .password((String) map.get("password"))
                    .authority((String) map.get("authority"))
                    .build());
        } catch (DataAccessException dataAccessException) {
            return Optional.empty();
        }
    }

    public void register(User user) {
        String sql = "INSERT INTO user(name, email, password, authority) VALUES(?, ?, ?, ?);";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword(), user.getAuthority());
    }

    public void update(User user) {
        String sql = "UPDATE USER SET name = ?, email = ?, authority = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getAuthority(), user.getId());
    }
}
