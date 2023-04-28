package com.example.security.config;


import com.example.security.filter.ValidateCodeFilter;
import com.example.security.handle.MyAuthenticationAccessDeniedHandler;
import com.example.security.handle.MyAuthenticationFailureHandler;
import com.example.security.handle.MyAuthenticationSucessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)//开启权限相关注解
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyAuthenticationSucessHandler myAuthenticationSucessHandler;
    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    @Autowired
    private MyAuthenticationAccessDeniedHandler myAuthenticationAccessDeniedHandler;
    @Autowired
    private ValidateCodeFilter validateCodeFilter;
    @Autowired
    private UserDetailsService userDetailService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin() // 表单方式
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .successHandler(myAuthenticationSucessHandler)
                .failureHandler(myAuthenticationFailureHandler)
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository()) // 配置 token 持久化仓库
                .tokenValiditySeconds(3600) // remember 过期时间，单为秒
                .userDetailsService(userDetailService) // 处理自动登录逻辑
                .and()
                .logout()
                .logoutUrl("/signout")//注销url
                .logoutSuccessUrl("/signout/success")//注销成功跳转url
                .deleteCookies("JSESSIONID")
                .and()
                .authorizeRequests() // 授权配置
                .antMatchers("/login.html","/code/image","**.html").permitAll()
                .anyRequest()  // 所有请求
                .authenticated() // 都需要认证
                .and().csrf().disable()
                .exceptionHandling().accessDeniedHandler(myAuthenticationAccessDeniedHandler);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;
    }

}
