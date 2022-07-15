package com.tang.security.config;

import com.tang.security.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserService userService;
    /**
     * 实现 AuthenticationManager 认证管理器
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.
//                inMemoryAuthentication().//使用内存中的 InMemoryUserDetailsManager
//                //passwordEncoder(NoOpPasswordEncoder.getInstance()).//不使用 PasswordEncoder 密码编码器
//                        withUser("admin").password(("admin")).roles("ADMIN").//配置 admin 用户
//                and().
//                withUser("normal").password(("normal")).roles("NORMAL").//配置 normal 用户
//                and().
//                withUser("user").password(("user")).roles("USER");//配置 normal 用户
        auth.userDetailsService(userService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
//        auth.authenticationProvider()
    }

    /**
     * 配置 URL 的权限控制
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()    //配置请求地址的权限
                .antMatchers("/security/echo").permitAll() //所有用户都可以访问/test/echo
                .antMatchers("/security/admin").hasRole("ADMIN")    //需要admin角色
                .antMatchers("/security/normal").access("hasRole('ROLE_NORMAL')")   //需要NORMAL角色
                .anyRequest().authenticated()   //任何请求，访问的用户都需要经过认证
                .and()
                .formLogin().permitAll()    //所有用户都可以访问/login
                .and()
                .logout().permitAll();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
