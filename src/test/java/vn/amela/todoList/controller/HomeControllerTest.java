package vn.amela.todoList.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
import vn.amela.todoList.model.User;

import java.security.Principal;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class HomeControllerTest {


    MockMvc mockMvc;

    @Autowired
    WebApplicationContext context;

    @Test
    void redirectLoginPage() throws Exception {
        String uri = "/";
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        Authentication auth = mock(Authentication.class);

        when(auth.getPrincipal()).thenReturn(new User("e", "", ""));

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).param("model", buildModel())).andReturn();
        Assertions.assertEquals("redirect:login", Objects.requireNonNull(result.getModelAndView()).getViewName());
    }


    @Test
    void redirectLoginPage_2() throws Exception {
        String uri = "/";
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        Authentication auth = mock(Authentication.class);

        when(auth.getPrincipal()).thenReturn(buildUserDetails(true));

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).param("model", buildModel())).andReturn();
        Assertions.assertEquals("redirect:/tasks", Objects.requireNonNull(result.getModelAndView()).getViewName());
    }

    @Test
    void redirectLoginPage_3() throws Exception {
        String uri = "/";
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        Authentication auth = mock(Authentication.class);

        when(auth.getPrincipal()).thenReturn(buildUserDetails(false));

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).param("model", buildModel())).andReturn();
        Assertions.assertEquals("redirect:login", Objects.requireNonNull(result.getModelAndView()).getViewName());
    }

    @Test
    void redirectLoginPage_4() throws Exception {
        String uri = "/";
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        Authentication auth = mock(Authentication.class);

        when(auth.getPrincipal()).thenReturn(buildPrincipal(false));

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).param("model", buildModel())).andReturn();
        Assertions.assertEquals("redirect:login", Objects.requireNonNull(result.getModelAndView()).getViewName());
    }

    @Test
    void redirectLoginPage_5() throws Exception {
        String uri = "/";
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        Authentication auth = mock(Authentication.class);

        when(auth.getPrincipal()).thenReturn(buildPrincipal(true));

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).param("model", buildModel())).andReturn();
        Assertions.assertEquals("redirect:/tasks", Objects.requireNonNull(result.getModelAndView()).getViewName());
    }

    @Test
    void redirectLoginPage_6() throws Exception {
        String uri = "/";
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        Authentication auth = mock(Authentication.class);

        when(auth.getPrincipal()).thenReturn("buildPrincipal(true)");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).param("model", buildModel())).andReturn();
        Assertions.assertEquals("redirect:/tasks", Objects.requireNonNull(result.getModelAndView()).getViewName());
    }

    @Test
    void redirectLoginPage_7() throws Exception {
        String uri = "/";
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        Authentication auth = mock(Authentication.class);

        when(auth.getPrincipal()).thenReturn("anonymousUser");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).param("model", buildModel())).andReturn();
        Assertions.assertEquals("redirect:login", Objects.requireNonNull(result.getModelAndView()).getViewName());
    }


    @Test
    void loginPage_1() throws Exception {
        String uri = "/login";
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        Authentication auth = mock(Authentication.class);

        when(auth.getPrincipal()).thenReturn("buildPrincipal(true)");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).param("model", buildModel())).andReturn();
        Assertions.assertEquals("redirect:/tasks/?page=1", Objects.requireNonNull(result.getModelAndView()).getViewName());
    }

    @Test
    void loginPage_2() throws Exception {
        String uri = "/login";
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        Authentication auth = mock(Authentication.class);

        when(auth.getPrincipal()).thenReturn("anonymousUser");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).param("model", buildModel())).andReturn();
        Assertions.assertEquals("Login.html", Objects.requireNonNull(result.getModelAndView()).getViewName());
    }

    @Test
    void loginPageF_1() throws Exception {
        String uri = "/login/false";
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        Authentication auth = mock(Authentication.class);

        when(auth.getPrincipal()).thenReturn("buildPrincipal(true)");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).param("model", buildModel())).andReturn();
        Assertions.assertEquals("redirect:/tasks/?page=1", Objects.requireNonNull(result.getModelAndView()).getViewName());
    }

    @Test
    void loginPageF_2() throws Exception {
        String uri = "/login/false";
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        Authentication auth = mock(Authentication.class);

        when(auth.getPrincipal()).thenReturn("anonymousUser");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).param("model", buildModel())).andReturn();
        Assertions.assertEquals("Login.html", Objects.requireNonNull(result.getModelAndView()).getViewName());
    }


    @Test
    void registerPage_1() throws Exception {
        String uri = "/register";
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        Authentication auth = mock(Authentication.class);

        when(auth.getPrincipal()).thenReturn("buildPrincipal(true)");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).param("model", buildModel())).andReturn();
        Assertions.assertEquals("redirect:/tasks/?page=1", Objects.requireNonNull(result.getModelAndView()).getViewName());
    }

    @Test
    void registerPage_2() throws Exception {
        String uri = "/register";
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn("anonymousUser");
        Process.notificationError = false;
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).param("model", buildModel())).andReturn();
        Assertions.assertEquals("Register.html", Objects.requireNonNull(result.getModelAndView()).getViewName());
    }

    @Test
    void registerPage_3() throws Exception {
        String uri = "/register";
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn("anonymousUser");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        Process.notificationError = true;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).param("model", buildModel())).andReturn();
        Assertions.assertEquals("Register.html", Objects.requireNonNull(result.getModelAndView()).getViewName());
    }

    private Object buildPrincipal(boolean flg) {
        return new Principal() {
            @Override
            public boolean equals(Object another) {
                return false;
            }

            @Override
            public String toString() {
                return null;
            }

            @Override
            public int hashCode() {
                return 0;
            }

            @Override
            public String getName() {
                if (flg)
                    return "dcvvd";
                return "anonymousUser";
            }
        };

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
}