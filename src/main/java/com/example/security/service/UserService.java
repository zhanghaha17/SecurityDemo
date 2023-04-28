package com.example.security.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface UserService {

    public List<Map<String,Object>> queryAllUser();
}
