package com.example.security.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<Map<String, Object>> queryAllUser() {
        String sql = "select * from t_user";
        return jdbcTemplate.queryForList(sql);
    }
}
