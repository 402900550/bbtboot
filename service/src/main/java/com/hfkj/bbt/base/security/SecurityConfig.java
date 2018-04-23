package com.hfkj.bbt.base.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by Administrator on 2018-01-10.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/doLogout").invalidateHttpSession(true).logoutSuccessUrl("/login.html").permitAll()
                .and()
                .formLogin().loginPage("/login.html").usernameParameter("userName")
                .passwordParameter("password").loginProcessingUrl("/doLogin")
                .successHandler(loginSuccessHandler()).failureHandler(loginFailHandler());

        http.csrf().disable();
        http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(false).expiredSessionStrategy(new MySessionInformationExpiredStrategy());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder  auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);

    }



    @Override
    public void configure(WebSecurity web) throws Exception {

        web.ignoring().antMatchers("/login.html","/bower_components/**"
                ,"/css/**","/dist/**","/img/**","/jquery/**","/js/**","/favicon.ico","/rest/**","/wxapi/**");
    }

    private LoginFailHandler loginFailHandler(){
        return new LoginFailHandler();
    }

    private LoginSuccessHandler loginSuccessHandler(){
        return new LoginSuccessHandler();
    }

}
