package com.mka.employeeProject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class AppSecurityConfig {

  @Bean
  public UserDetailsManager userDetailsManager(DataSource dataSource) {

    JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

    // define query to retrieve a user by username
    jdbcUserDetailsManager.setUsersByUsernameQuery(
        "select user_id, password, active from members where user_id=?"
    );
    // define query to retrieve the authorities/roles by username
    jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
        "select user_id, role from roles where user_id=?"
    );

    return jdbcUserDetailsManager;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeRequests(
        configurer ->
            configurer
                .requestMatchers(HttpMethod.GET, "/api/employees").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/api/employees/**").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.POST, "/api/employees").hasRole("MANAGER")
                .requestMatchers(HttpMethod.PUT, "/api/employees/**").hasRole("MANAGER")
                .requestMatchers(HttpMethod.DELETE, "/api/employees/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/employeeDetails").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/api/employeeDetails/**").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.POST, "/api/employeeDetails").hasRole("MANAGER")
                .requestMatchers(HttpMethod.PUT, "/api/employeeDetails/**").hasRole("MANAGER")
                .requestMatchers(HttpMethod.DELETE, "/api/employeeDetails/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/employeeBenefits").hasRole("MANAGER")
                .requestMatchers(HttpMethod.GET, "/api/employeeBenefits/**").hasRole("EMPLOYEE") // should be restricted to only his benefit
                .requestMatchers(HttpMethod.POST, "/api/employeeBenefits").hasRole("MANAGER")
                .requestMatchers(HttpMethod.PUT, "/api/employeeBenefits/**").hasRole("MANAGER")
                .requestMatchers(HttpMethod.DELETE, "/api/employeeBenefits/**").hasRole("ADMIN")
    );

    // use HTTP Basic authentication
    http.httpBasic(Customizer.withDefaults());

    // disable Cross Site Request Forgery (CSRF)
    http.csrf(csrf ->csrf.disable());

    return http.build();
  }
}
