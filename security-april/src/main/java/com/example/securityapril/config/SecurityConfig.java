package com.example.securityapril.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){
      UserDetails studnet1 = User.builder()
                .username("Mahesh")
                .password(passwordEncoder.encode("abc"))
                .roles("STUDENT")
                .build();
      UserDetails admin1=User.builder()
              .username("Deepak")
              .password(passwordEncoder.encode("abcd"))
              .roles("ADMIN")
              .build();
      UserDetails student2= User.builder()
              .username("Akash")
              .password(passwordEncoder.encode("abcde"))
              .roles("STUDENT")
              .build();

      return new InMemoryUserDetailsManager(studnet1,student2,admin1);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean//
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        return httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/app/welcome")
                .permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/app/student","/api/v1/app/admin")
                .authenticated()
                .and().formLogin().and()
                .build();
    }
    //DAOAuthentication provider for DB security
}
