package com.kalyan.springsecurity.security;

import com.kalyan.springsecurity.jwt.JwtAuthenticationFilter;
import com.kalyan.springsecurity.services.CustumUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig  {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;




@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}


 @Bean
 public SecurityFilterChain  securityFilterChain(HttpSecurity http) throws Exception {
     return
             http.authorizeHttpRequests(
                     req->req.requestMatchers("/home").authenticated()
                     .requestMatchers("/auth/login", "/register","/h2-console/**","/open").permitAll()

                     .anyRequest().authenticated())
                     .formLogin(Customizer.withDefaults())
                     .httpBasic(Customizer.withDefaults())
                     .logout(LogoutConfigurer::permitAll)
                     .csrf(AbstractHttpConfigurer::disable)
                     
                     .build();
 }

 @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, CustumUserDetailsService custumUserDetailsService) throws Exception{
    AuthenticationManagerBuilder authenticationManagerBuilder =
            http.getSharedObject(AuthenticationManagerBuilder.class);

    authenticationManagerBuilder.userDetailsService(custumUserDetailsService);
    return authenticationManagerBuilder.build();


 }
}
