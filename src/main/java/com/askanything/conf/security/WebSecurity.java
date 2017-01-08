package com.askanything.conf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import javax.sql.DataSource;


@Configuration
@ComponentScan({ "com.askanything.models.entitys", "com.askanything.conf" })
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final DataSource dbase;

    @Autowired
    public WebSecurity(DataSource dbase) {
        this.dbase = dbase;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/registration").access("permitAll")
                .antMatchers("/resources/**").access("permitAll")
                .antMatchers("/user/answers").authenticated()
                .antMatchers("/user/*").access("permitAll")
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").permitAll().and().csrf()
                .csrfTokenRepository(csrfTokenRepository());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dbase);
    }


    private CsrfTokenRepository csrfTokenRepository()
    {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName("_csrf");
        return repository;
    }

}
