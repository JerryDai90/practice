package com.practice.spring.core.transaction.bean;

import com.practice.spring.core.transaction.bean.IUserService;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author jerry dai
 * @date 2023-07-11- 09:48
 */
public class UserServiceImpl implements IUserService {

    JdbcTemplate jdbcTemplate;

    @Override
    public List query() {
        return null;
    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
