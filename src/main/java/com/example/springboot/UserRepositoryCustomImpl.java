package com.example.springboot;

import com.example.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryCustomImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean isExisted(String name) {
        String sql = "SELECT COUNT(*) FROM user WHERE name = ?";
        return 0 != jdbcTemplate.queryForObject(sql, Integer.class, name);
    }

    public User findByName(String name) {
        String sql = "SELECT * FROM user WHERE name = ?";
        Map<String, Object> map = jdbcTemplate.queryForMap(sql, name);
        User user = new User();
        user.setName((String) map.get("name"));
        user.setEmail((String) map.get("email"));
        user.setPassword((String) map.get("password"));
        user.setAuthority((String) map.get("authority"));
        return user;
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
