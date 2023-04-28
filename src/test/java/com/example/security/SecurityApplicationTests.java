package com.example.security;

import com.example.security.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class SecurityApplicationTests {

	@Autowired
	private UserDao userDao;

	@Test
	public void contextLoads() {

		List<Map<String, Object>> maps = userDao.queryAllUser();
		System.out.println(maps.size());
	}

}
