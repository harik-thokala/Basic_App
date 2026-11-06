package com.example.workingApp.dao;

import com.example.workingApp.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository // Tells Spring to manage this class automatically
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    // Spring injects JdbcTemplate automatically!
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // This maps a database row to our User object
    private final RowMapper<User> userRowMapper = (rs, rowNum) -> 
            new User(rs.getInt("id"), rs.getString("name"));

    public int insert(User user) {
        String sql = "INSERT INTO users (name) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            int id = keyHolder.getKey().intValue();
            user.setId(id);
            return id;
        }
        return -1;
    }

    public Optional<User> findById(int id) {
        String sql = "SELECT id, name FROM users WHERE id = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, id);
        return users.stream().findFirst();
    }

    public List<User> findAll() {
        String sql = "SELECT id, name FROM users ORDER BY id";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    public boolean update(User user) {
        String sql = "UPDATE users SET name = ? WHERE id = ?";
        return jdbcTemplate.update(sql, user.getName(), user.getId()) > 0;
    }

    public boolean deleteById(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }
}