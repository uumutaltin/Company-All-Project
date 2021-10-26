package com.works.config;

import com.works.services.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
                .antMatchers("/main/**").permitAll()
                .antMatchers("/advertisement/**").permitAll()
                .antMatchers("/announcement/**").permitAll()
                .antMatchers("/contents/**").permitAll()
                .antMatchers("/customer/**").permitAll()
                .antMatchers("/gallery/**").permitAll()
                .antMatchers("/home/**").hasAnyRole("MVC","REST")
                .antMatchers("/likes/**").permitAll()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/news/**").permitAll()
                .antMatchers("/orders/**").permitAll()
                .antMatchers("/products/**").permitAll()
                .antMatchers("/register/**").permitAll()
                .antMatchers("/survey/**").permitAll()
                .antMatchers("/error/**").permitAll()
                .antMatchers("/utils/**").permitAll()

                .antMatchers("/api/advertisement/**").hasAnyRole("MVC","REST")
                .antMatchers("/api/category/**").hasAnyRole("MVC","REST")
                .antMatchers("/api/contents/**").hasAnyRole("MVC","REST")
                .antMatchers("/api/customer/**").hasAnyRole("MVC","REST")
                .antMatchers("/api/like/**").hasAnyRole("MVC","REST")
                .antMatchers("/api/news/**").hasAnyRole("MVC","REST")
                .antMatchers("/api/orders/**").hasAnyRole("MVC","REST")
                .antMatchers("/api/products/**").hasAnyRole("MVC","REST")
                .antMatchers("/api/survey/**").hasAnyRole("MVC","REST")
                .antMatchers("/api/users/**").hasAnyRole("MVC","REST")

                .antMatchers("/static/**").permitAll()
                .antMatchers("/templates/**").permitAll()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/inc/**").permitAll()

                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .defaultSuccessUrl("/home", true)
                .permitAll()
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
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers(
     "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/webjars/**");
    }




}
