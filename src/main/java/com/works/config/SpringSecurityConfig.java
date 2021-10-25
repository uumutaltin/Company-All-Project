package com.works.config;

import com.works.services.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    final UserService userService;
    public SpringSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(userService.encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/advertisement/**").hasRole("MVC")
                .antMatchers("/announcement/**").hasRole("REST")
                .antMatchers("/contents/**").permitAll()
                .antMatchers("/customer/**").permitAll()
                .antMatchers("/gallery/**").permitAll()
                .antMatchers("/home/**").permitAll()
                .antMatchers("/likes/**").permitAll()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/news/**").permitAll()
                .antMatchers("/orders/**").permitAll()
                .antMatchers("/products/**").permitAll()
                .antMatchers("/register/**").permitAll()
                .antMatchers("/survey/**").permitAll()
                .antMatchers("/error/**").permitAll()
                .antMatchers("/utils/**").permitAll()
                .antMatchers("/api/users/**").hasAnyRole("MVC","REST")
                .antMatchers("/api/contents/**").hasAnyRole("MVC","REST")
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin().loginPage("/login").defaultSuccessUrl("/home", true).permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .logoutSuccessHandler(userService)
                .logoutSuccessUrl("/login")
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403");

        http.csrf().disable();

    }
}
