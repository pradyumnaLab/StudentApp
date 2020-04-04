package com.pk.StudentApp.Security;
import static com.pk.StudentApp.Security.ApplicationUserPermission.*;
import static com.pk.StudentApp.Security.ApplicationUserRole.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@Configuration
public class BasicAuthSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;

    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name())
                .antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(STUDENT_WRITE.name())
                .antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(STUDENT_WRITE.name())
                .antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(STUDENT_WRITE.name())
                .antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(ADMIN.name(), STUDENT.name(), ADMIN_TRAINEE.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails pkUserDetails =  User.builder()
                .username("pk")
                .password(passwordEncoder.encode("password"))
                .roles(ApplicationUserRole.STUDENT.name())
   //             .authorities(STUDENT.getAuthorities())
                .build();

        UserDetails arupUserDetails =  User.builder()
                .username("Arup")
                .password(passwordEncoder.encode("password"))
                .roles(ApplicationUserRole.ADMIN_TRAINEE.name())
    //            .authorities(ADMIN_TRAINEE.getAuthorities())
                .build();

        UserDetails adminUser =  User.builder()
                .username("admin")
                .password(passwordEncoder.encode("password-1"))
         //       .roles(ApplicationUserRole.ADMIN.name())
                .authorities(ADMIN.getAuthorities())
                .build();

        return new InMemoryUserDetailsManager(pkUserDetails, adminUser, arupUserDetails);
    }
}
