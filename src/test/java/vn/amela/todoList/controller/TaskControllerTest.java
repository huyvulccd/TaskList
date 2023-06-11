package vn.amela.todoList.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;
import vn.amela.todoList.dto.Process;
import vn.amela.todoList.dto.Search;
import vn.amela.todoList.mapper.TaskMapper;
import vn.amela.todoList.model.Task;
import vn.amela.todoList.model.User;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class TaskControllerTest {
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext context;

    @Autowired
    TaskMapper taskMapper;

    @Test
    void getListTask() throws Exception {
        String uri = "/tasks";
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        mockAuthentication(false);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).param("model", buildModel()).param("page", "1")).andReturn();
        Assertions.assertEquals("redirect:/", Objects.requireNonNull(result.getModelAndView()).getViewName());
    }

    @Test
    void getListTask_1() throws Exception {
        String uri = "/tasks";
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        mockAuthentication(true);
        Search.status = 0;
        Process.notificationError = false;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).param("model", buildModel()).param("page", "1")).andReturn();
        Assertions.assertEquals("TaskList.html", Objects.requireNonNull(result.getModelAndView()).getViewName());
    }

    @Test
    void getListTask_2() throws Exception {
        String uri = "/tasks";
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        mockAuthentication(true);
        Search.status = 1;
        Process.notificationError = false;

        createTasks(1);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).param("model", buildModel()).param("page", "1")).andReturn();
        Assertions.assertEquals("TaskList.html", Objects.requireNonNull(result.getModelAndView()).getViewName());

        removeTask(1);
    }

    @Test
    void getListTask_3() throws Exception {
        String uri = "/tasks";
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        mockAuthentication(true);
        Search.status = 1;
        Search.title  = "dumb";
        Process.notificationError = false;

        createTasks(30);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).param("model", buildModel()).param("page", "1")).andReturn();
        Assertions.assertEquals("TaskList.html", Objects.requireNonNull(result.getModelAndView()).getViewName());

        removeTask(30);
    }

    @Test
    void getListFilterTasks() throws Exception {
        String uri = "/tasks/filter";
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        mockAuthentication(true);
        Search.status = 1;
        Search.title  = "dumb";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri).param("model", buildModel()).param("page", "1")).andReturn();
        Assertions.assertEquals("redirect:/tasks", Objects.requireNonNull(result.getModelAndView()).getViewName());
    }

    @Test
    void postTask() throws Exception {
        String uri = "/tasks";
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

//        mockAuthentication(true);
        Search.status = 1;
        Search.title  = "dumb";

        User user = new User();
        user.setId(-1L);
        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(user).thenReturn(user);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri).param("title", "")).andReturn();
        Assertions.assertEquals("redirect:/tasks/?page=1", Objects.requireNonNull(result.getModelAndView()).getViewName());
    }

    @Test
    void postTask2() throws Exception {
        String uri = "/tasks";
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        mockAuthentication(true);
        Search.status = 1;
        Search.title  = "dumb";

        User user = new User();
        user.setId(-1L);
        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(user).thenReturn(user);
        ((User) auth.getPrincipal()).setId(-1L);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri).param("title", "dumbe")).andReturn();
        Assertions.assertEquals("redirect:/tasks", Objects.requireNonNull(result.getModelAndView()).getViewName());
    }

    @Test
    void test_create_update_delete() throws Exception {
        createTasks(1);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        String uriPost = "/tasks/edit/1";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uriPost)
                .param("title", "dube").param("id", "-1")).andReturn();

        String uriDelete = "/tasks/delete/-1";
        MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders.post(uriDelete).param("id", "-1")).andReturn();
    }

    private String buildServlet() {
        return new HttpServletResponse() {
            @Override
            public String getCharacterEncoding() {
                return null;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public ServletOutputStream getOutputStream() throws IOException {
                return null;
            }

            @Override
            public PrintWriter getWriter() throws IOException {
                return null;
            }

            @Override
            public void setCharacterEncoding(String charset) {

            }

            @Override
            public void setContentLength(int len) {

            }

            @Override
            public void setContentLengthLong(long length) {

            }

            @Override
            public void setContentType(String type) {

            }

            @Override
            public void setBufferSize(int size) {

            }

            @Override
            public int getBufferSize() {
                return 0;
            }

            @Override
            public void flushBuffer() throws IOException {

            }

            @Override
            public void resetBuffer() {

            }

            @Override
            public boolean isCommitted() {
                return false;
            }

            @Override
            public void reset() {

            }

            @Override
            public void setLocale(Locale loc) {

            }

            @Override
            public Locale getLocale() {
                return null;
            }

            @Override
            public void addCookie(Cookie cookie) {

            }

            @Override
            public boolean containsHeader(String name) {
                return false;
            }

            @Override
            public String encodeURL(String url) {
                return null;
            }

            @Override
            public String encodeRedirectURL(String url) {
                return null;
            }

            @Override
            public String encodeUrl(String url) {
                return null;
            }

            @Override
            public String encodeRedirectUrl(String url) {
                return null;
            }

            @Override
            public void sendError(int sc, String msg) throws IOException {

            }

            @Override
            public void sendError(int sc) throws IOException {

            }

            @Override
            public void sendRedirect(String location) throws IOException {

            }

            @Override
            public void setDateHeader(String name, long date) {

            }

            @Override
            public void addDateHeader(String name, long date) {

            }

            @Override
            public void setHeader(String name, String value) {

            }

            @Override
            public void addHeader(String name, String value) {

            }

            @Override
            public void setIntHeader(String name, int value) {

            }

            @Override
            public void addIntHeader(String name, int value) {

            }

            @Override
            public void setStatus(int sc) {

            }

            @Override
            public void setStatus(int sc, String sm) {

            }

            @Override
            public int getStatus() {
                return 0;
            }

            @Override
            public String getHeader(String name) {
                return null;
            }

            @Override
            public Collection<String> getHeaders(String name) {
                return null;
            }

            @Override
            public Collection<String> getHeaderNames() {
                return null;
            }
        }.toString();
    }

    private void createTasks(int size) {
        for (int i = 1; i <= size; i++) {
            Task task = new Task();
            task.setId_user(-1L);
            task.setStatus(1);
            task.setTitle("dumb");
            task.setId((long) -i);
            taskMapper.creatTask(task);
        }
    }

    private void removeTask(int size) {
        for (int i = 1; i <= size; i++) {
            taskMapper.deleteById((long) -i);
        }
    }

    private void mockAuthentication(boolean flg) {
        User user = new User();
        user.setId(-1L);

        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(buildUserDetails(flg)).thenReturn(user);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
    }

    private String buildModel() {
        return new Model() {
            @Override
            public Model addAttribute(String attributeName, Object attributeValue) {
                return null;
            }

            @Override
            public Model addAttribute(Object attributeValue) {
                return null;
            }

            @Override
            public Model addAllAttributes(Collection<?> attributeValues) {
                return null;
            }

            @Override
            public Model addAllAttributes(Map<String, ?> attributes) {
                return null;
            }

            @Override
            public Model mergeAttributes(Map<String, ?> attributes) {
                return null;
            }

            @Override
            public boolean containsAttribute(String attributeName) {
                return false;
            }

            @Override
            public Object getAttribute(String attributeName) {
                return null;
            }

            @Override
            public Map<String, Object> asMap() {
                return null;
            }
        }.toString();
    }

    private Object buildUserDetails(boolean flg) {
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
                if (flg)
                    return "dcvvd";
                return "anonymousUser";
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }
        };
    }
}