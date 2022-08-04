package vn.amela.todoList.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Configuration
@EnableWebSecurity
public class SercureConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()

                // Cho phép các request static không bị ràng buộc
                .antMatchers( "/manager/**", "/upload/**").permitAll()

                // Các request kiểu "/admin/" phải đăng nhập
                /*.antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/user/home").hasRole("GUEST")*/
                .and()

                // Cấu hình trang đăng nhập
                .formLogin().loginPage("/").loginProcessingUrl("/account/login").

                defaultSuccessUrl("/tasks", true)
                .failureUrl("/login/false")
                .permitAll()//

                .and()

                //Cấu hình trang logout
                .logout().logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true)
                .deleteCookies("JSESSIONID").permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder(4));//mã hóa
    }

    public static void main(String[] args) {
//        System.out.println(new BCryptPasswordEncoder(4).encode("".trim()));
        System.out.println("Cộng hoà xã hội chủ nghĩa Việt Nam");
    }
}
