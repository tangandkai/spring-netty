package com.tang.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder_(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Override
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 实现 AuthenticationManager 认证管理器
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                inMemoryAuthentication().//使用内存中的 InMemoryUserDetailsManager
                passwordEncoder(new CustomPasswordEncoder()).
                //passwordEncoder(NoOpPasswordEncoder.getInstance()).//不使用 PasswordEncoder 密码编码器
                withUser("admin").password(("admin")).roles("ADMIN").//配置 admin 用户
                and().
                withUser("normal").password(("normal")).roles("NORMAL").//配置 normal 用户
                and().
                withUser("user").password(("user")).roles("USER");//配置 normal 用户
    }

    /**
     * 配置 URL 的权限控制
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()//配置请求地址的权限
                .antMatchers("/auth/echo").permitAll()//所有用户可访问
                .antMatchers("/auth/admin").hasRole("ADMIN")//需要 ADMIN 角色
                .antMatchers("/auth/normal").access("hasRole('ROLE_NORMAL')")//需要 NORMAL 角色
                .anyRequest().authenticated()//任何请求，访问的用户都需要经过认证
                .and()
                .formLogin().loginPage("/login").permitAll()//设置 Form 表单登录,所有用户可访问
                .and()
                .logout().permitAll();

    }
}

class CustomPasswordEncoder implements PasswordEncoder{

    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(charSequence.toString());
    }
}