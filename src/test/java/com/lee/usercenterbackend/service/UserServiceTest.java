package com.lee.usercenterbackend.service;
import java.util.Date;

import com.lee.usercenterbackend.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  用户服务测试
 *
 *
 */
@SpringBootTest
public class UserServiceTest {
    @Resource
    private UserService userService;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setId(0L);
        user.setUsername("doglee");
        user.setUserAccount("123");
        user.setAvatarUrl("");
        user.setGender(0);
        user.setUserPassword("xxx");
        user.setPhone("123");
        user.setEmail("456");
        user.setUserStatus(0);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsDelete(0);

        boolean ret = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(ret);
    }

    @Test
    void userRegister() {
        String username = "doglebronto";
        String password = "";
        String checkPassword = "123456";
        String planetCode = "1";
        long l = userService.userRegister(username, password, checkPassword, planetCode);
        Assertions.assertEquals(-1, l);
        username = "dog";
        l = userService.userRegister(username, password, checkPassword, planetCode);
        Assertions.assertEquals(-1, l);
        username = "doglebronto";
        password = "123456";
        l = userService.userRegister(username, password, checkPassword, planetCode);
        Assertions.assertEquals(-1, l);
        username = "dog lebronto";
        password = "12345678";
        l = userService.userRegister(username, password, checkPassword, planetCode);
        Assertions.assertEquals(-1, l);
        checkPassword = "123456789";
        l = userService.userRegister(username, password, checkPassword, planetCode);
        Assertions.assertEquals(-1, l);
        username = "doglee";
        checkPassword = "12345678";
        l = userService.userRegister(username, password, checkPassword, planetCode);
        Assertions.assertEquals(-1, l);
        username = "lebronto";
        l = userService.userRegister(username, password, checkPassword, planetCode);
        Assertions.assertEquals(-1, l);

    }
}