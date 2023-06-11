package vn.amela.todoList.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import vn.amela.todoList.model.User;

import java.time.Instant;
import java.util.Objects;

@ContextConfiguration
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class AccountControllerTest {

    MockMvc mockMvc;

    @Autowired
    WebApplicationContext context;

    @Test
    void doRegister_1() throws Exception {
        String uri = "/account/register";
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        User user = new User();
        user.setUsername("");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri).param("user", user.toString())).andReturn();
        Assertions.assertEquals("redirect:/register", Objects.requireNonNull(result.getModelAndView()).getViewName());
    }

    @Test
    void doRegister_2() throws Exception {
        String uri = "/account/register";
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri).param("username", "userhd")).andReturn();
        Assertions.assertEquals("redirect:/register", Objects.requireNonNull(result.getModelAndView()).getViewName());
    }

    @Test
    void doRegister_3() throws Exception {
        String uri = "/account/register";
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri).param("username", "userhd")
                .param("password", "652usrfer")).andReturn();
        Assertions.assertEquals("redirect:/register", Objects.requireNonNull(result.getModelAndView()).getViewName());
    }

    @Test
    void doRegister_4() throws Exception {
        String uri = "/account/register";
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        long time = Instant.now().toEpochMilli();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri).param("username", "userhd" + time)
                .param("password", "652usrfer")
                .param("name", "Nguyen Van A")).andReturn();
        Assertions.assertEquals("redirect:/", Objects.requireNonNull(result.getModelAndView()).getViewName());

        MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders.post(uri).param("username", "userhd" + time)
                .param("password", "652usrfer")
                .param("name", "Nguyen Van A")).andReturn();
        Assertions.assertEquals("redirect:/register", Objects.requireNonNull(result1.getModelAndView()).getViewName());

        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.post(uri).param("username", "userhda" + time)
                .param("password", "652usrfer")
                .param("name", "Nguyen Van A")).andReturn();
        Assertions.assertEquals("redirect:/register", Objects.requireNonNull(result1.getModelAndView()).getViewName());
    }

}