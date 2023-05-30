package com.example.springboot;

import com.example.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
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

    public UserDetailsImpl findByName(String name) {
        String sql = "SELECT * FROM user WHERE name = ?";
        Map<String, Object> map = jdbcTemplate.queryForMap(sql, name);
        String password = (String) map.get("password");
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority((String) map.get("authority")));
        return new UserDetailsImpl(name, password, authorities);
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
