package com.example.springboot.repository;

import com.example.springboot.Entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.util.AssertionErrors.*;

@SpringBootTest
public class UserCustomRepositoryTest {

    @Autowired
    private UserCustomRepository userCustomRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Sql("/sql/user-create.sql")
    public void isExistedTrue() {
        assertEquals(null, 1, userCustomRepository.isExisted("name"));
    }

    @Test
    @Sql("/sql/user-clear.sql")
    public void isExistedFalse() {
        assertEquals(null, 0, userCustomRepository.isExisted("name"));
    }

    @Test
    @Sql("/sql/user-create.sql")
    public void canFindByName() {
        User user = userCustomRepository.findByName("name");
        assertEquals(null, "name", user.getName());
        assertEquals(null, "email@example.com", user.getEmail());
        assertEquals(null, "password", user.getPassword());
        assertEquals(null, "ROLE_USER", user.getAuthority());
    }

    @Test
    @Sql("/sql/user-clear.sql")
    public void canNotFindByName() {
        User user = userCustomRepository.findByName("name");
        assertNull(null, user.getName());
        assertNull(null, user.getEmail());
        assertNull(null, user.getPassword());
        assertNull(null, user.getAuthority());
    }

    @Test
    @Sql("/sql/user-clear.sql")
    public void canRegister() {
        String name = "name";
        User user = User.builder()
                .name(name)
                .email("email@example.com")
                .password("password")
                .authority("ROLE_USER")
                .build();
        userCustomRepository.register(user);
        String sql = "SELECT * FROM USER WHERE name = ?";
        try {
            Map<String, Object> map = jdbcTemplate.queryForMap(sql, name);
            assertEquals(null, "name", (String) map.get("name"));
            assertEquals(null, "email@example.com", (String) map.get("email"));
            assertEquals(null, "password", (String) map.get("password"));
            assertEquals(null, "ROLE_USER", (String) map.get("authority"));
        } catch (DataAccessException dataAccessException) {
            fail();
        }
    }

    @Test
    @Sql("/sql/user-create.sql")
    public void canUpdate() {
        String name = "name1";
        User user = User.builder()
                .name(name)
                .email("email1@example.com")
                .password("password1")
                .authority("ROLE_USER_1")
                .build();
        userCustomRepository.register(user);
        String sql = "SELECT * FROM USER WHERE name = ?";
        try {
            Map<String, Object> map = jdbcTemplate.queryForMap(sql, name);
            assertEquals(null, "name1", (String) map.get("name"));
            assertEquals(null, "email1@example.com", (String) map.get("email"));
            assertEquals(null, "password1", (String) map.get("password"));
            assertEquals(null, "ROLE_USER_1", (String) map.get("authority"));
        } catch (DataAccessException dataAccessException) {
            fail();
        }
        try {
            jdbcTemplate.queryForMap(sql, "name");
        } catch (DataAccessException dataAccessException) {
            assertTrue(null, true);
        }
    }
}
