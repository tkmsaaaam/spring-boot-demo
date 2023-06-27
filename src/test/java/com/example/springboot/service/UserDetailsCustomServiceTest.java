package com.example.springboot.service;

import com.example.springboot.Entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.util.AssertionErrors.*;

@SpringBootTest
public class UserDetailsCustomServiceTest {

    @Autowired
    private UserDetailsCustomService userDetailsCustomService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    @Sql("/sql/user-clear.sql")
    public void canRegisterByStrings() {
        userDetailsCustomService.register("username", "email@example.com", "password");
        String sql = "SELECT * FROM USER";
        try {
            Map<String, Object> map = jdbcTemplate.queryForMap(sql);
            assertEquals(null, "username", map.get("name"));
            assertEquals(null, "email@example.com", map.get("email"));
            assertTrue(null, passwordEncoder.matches("password", (String) map.get("password")));
            assertEquals(null, "ROLE_USER", map.get("authority"));
        } catch (DataAccessException dataAccessException) {
            fail();
        }
    }

    @Test
    @Sql("/sql/user-clear.sql")
    public void canRegisterByUser() {
        User user = User.builder()
                .name("username")
                .email("email@example.com")
                .password("password")
                .build();
        userDetailsCustomService.register(user);
        String sql = "SELECT * FROM USER";
        try {
            Map<String, Object> map = jdbcTemplate.queryForMap(sql);
            assertEquals(null, "username", map.get("name"));
            assertEquals(null, "email@example.com", map.get("email"));
            assertTrue(null, passwordEncoder.matches("password", (String) map.get("password")));
            assertEquals(null, "ROLE_USER", map.get("authority"));
        } catch (DataAccessException dataAccessException) {
            fail();
        }
    }

    @Test
    @Sql("/sql/user-create.sql")
    public void isExistUserTrue() {
        boolean bool = userDetailsCustomService.isExistUser("name");
        assertTrue(null, bool);
    }

    @Test
    @Sql("/sql/user-clear.sql")
    public void isExistUserFalse() {
        boolean bool = userDetailsCustomService.isExistUser("name");
        assertFalse(null, bool);
    }

    @Test
    @Sql("/sql/user-clear.sql")
    public void findAllIsEmpty() {
        List<User> userList = userDetailsCustomService.findAll();
        assertTrue(null, userList.isEmpty());
    }

    @Test
    @Sql("/sql/user-create.sql")
    public void findAllReturnsAUser() {
        List<User> userList = userDetailsCustomService.findAll();
        assertEquals(null, 1, userList.size());
        User user = userList.get(0);
        assertEquals(null, "name", user.getName());
        assertEquals(null, "email@example.com", user.getEmail());
        assertEquals(null, "password", user.getPassword());
        assertEquals(null, "ROLE_USER", user.getAuthority());
    }

    @Test
    @Sql("/sql/users-create.sql")
    public void findAllReturnsUsers() {
        List<User> userList = userDetailsCustomService.findAll();
        assertEquals(null, 2, userList.size());
        User user = userList.get(0);
        assertEquals(null, "name", user.getName());
        assertEquals(null, "email@example.com", user.getEmail());
        assertEquals(null, "password", user.getPassword());
        assertEquals(null, "ROLE_USER", user.getAuthority());
        User user1 = userList.get(1);
        assertEquals(null, "name1", user1.getName());
        assertEquals(null, "email1@example.com", user1.getEmail());
        assertEquals(null, "password1", user1.getPassword());
        assertEquals(null, "ROLE_USER", user1.getAuthority());
    }

    @Test
    @Sql("/sql/users-create.sql")
    public void canFindById() {
        String sql = "SELECT ID FROM USER WHERE NAME = ?";
        Integer id = jdbcTemplate.queryForObject(sql, Integer.class, "name");
        try {
            Optional<User> user = userDetailsCustomService.findById(id);
            if (user.isPresent()) {
                assertEquals(null, "name", user.get().getName());
                assertEquals(null, "email@example.com", user.get().getEmail());
                assertEquals(null, "password", user.get().getPassword());
                assertEquals(null, "ROLE_USER", user.get().getAuthority());
            } else {
                fail();
            }
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    @Sql("/sql/users-create.sql")
    public void canDelete() {
        String sql = "SELECT ID FROM USER WHERE NAME = ?";
        Integer id = jdbcTemplate.queryForObject(sql, Integer.class, "name");
        try {
            userDetailsCustomService.deleteById(id);
        } catch (Exception e) {
            fail();
        }
    }
}
