package vn.amela.todoList.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import vn.amela.todoList.mapper.UserMapper;
import vn.amela.todoList.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @MockBean
    private UserMapper userMapper;

    @Autowired
    private UserServiceImpl service;

    @Test
    void registerAccount_feildEmpty1_should_returnFalse() {
        User user = new User();
        user.setName("1");
        user.setPassword("");
        user.setUsername("");
        service.RegisterAccount(user);
    }

    @Test
    void registerAccount_feildEmpty2_should_returnFalse() {
        User user = new User();
        user.setName("");
        user.setPassword("1");
        user.setUsername("");
        service.RegisterAccount(user);
    }

    @Test
    void registerAccount_feildEmpty3_should_returnFalse() {
        User user = new User();
        user.setName("1");
        user.setPassword("1");
        user.setUsername("");
        service.RegisterAccount(user);
    }

    @Test
    void registerAccount_feildEmpty4_should_returnFalse() {
        User user = new User();
        user.setName("");
        user.setPassword("");
        user.setUsername("1");
        service.RegisterAccount(user);
    }

    @Test
    void registerAccount_feildEmpty5_should_returnFalse() {
        User user = new User();
        user.setName("1");
        user.setPassword("");
        user.setUsername("1");
        service.RegisterAccount(user);
    }

    @Test
    void registerAccount_feildEmpty6_should_returnFalse() {
        User user = new User();
        user.setName("");
        user.setPassword("1");
        user.setUsername("1");
        service.RegisterAccount(user);
    }

    @Test
    void registerAccount_feildEmpty7_should_returnFalse() {
        User user = new User();
        user.setName("1");
        user.setPassword("1");
        user.setUsername("1");
        service.RegisterAccount(user);
    }

    @Test
    void registerAccount_duplicateUserName_should_returnFalse() {
        User user = new User();
        user.setName("1");
        user.setPassword("1");
        user.setUsername("ATKTA1234");
        service.RegisterAccount(user);
    }

    @Test
    void registerAccount_Database_empty_should_returnFalse() {
        User user = new User();
        user.setName("1");
        user.setPassword("1");
        user.setUsername("ATKTA1234");
        when(userMapper.findAll()).thenReturn(List.of());
        service.RegisterAccount(user);
    }

    @Test
    void findByUsername() {
    }
}