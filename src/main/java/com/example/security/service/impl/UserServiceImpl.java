package com.example.security.service.impl;

import com.example.security.dao.UserDao;
import com.example.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Override
    public List<Map<String, Object>> queryAllUser() {
        return userDao.queryAllUser();
    }
}
