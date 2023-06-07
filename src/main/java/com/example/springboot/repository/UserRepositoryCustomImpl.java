package com.example.springboot.repository;

import com.example.springboot.model.User;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@AllArgsConstructor
@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    private final JdbcTemplate jdbcTemplate;

    public boolean isExisted(String name) {
        String sql = "SELECT COUNT(*) FROM user WHERE name = ?";
        return 0 != jdbcTemplate.queryForObject(sql, Integer.class, name);
    }

    public User findByName(String name) {
        String sql = "SELECT * FROM user WHERE name = ?";
        Map<String, Object> map = jdbcTemplate.queryForMap(sql, name);
        return User.builder()
                .name((String) map.get("name"))
                .email((String) map.get("email"))
                .password((String) map.get("password"))
                .authority((String) map.get("authority"))
                .build();
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
