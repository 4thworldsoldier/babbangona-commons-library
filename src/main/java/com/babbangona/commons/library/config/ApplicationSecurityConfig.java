package com.babbangona.commons.library.config;

import com.babbangona.commons.library.exceptions.handler.CustomAccessDeniedHandler;
import com.babbangona.commons.library.utils.CustomOAuth2SuccessHandler;
import com.babbangona.commons.library.repo.RoleRepository;
import com.babbangona.commons.library.repo.TenantRepository;
import com.babbangona.commons.library.repo.UserRepository;
import com.babbangona.commons.library.security.JwtUtil;
import com.babbangona.commons.library.security.filter.JwtAuthenticationFilter;
import com.babbangona.commons.library.security.filter.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ApplicationSecurityConfig {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .authorizeHttpRequests(requests ->
                        requests.requestMatchers("/api/auth/**", "/login/**", "/oauth2/**","/post-login","/favicon.ico").permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .exceptionHandling(exception ->
                        exception.accessDeniedHandler(customAccessDeniedHandler)
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(customOAuth2SuccessHandler(userRepository, tenantRepository, roleRepository, userDetailsService, jwtUtil, passwordEncoder()))
                )
                .addFilter(new JwtAuthenticationFilter(authenticationManager(httpSecurity.getSharedObject(AuthenticationConfiguration.class)), jwtUtil))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(httpSecurity.getSharedObject(AuthenticationConfiguration.class)), jwtUtil, userDetailsService));
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomOAuth2SuccessHandler customOAuth2SuccessHandler(UserRepository userRepository,
                                                                 TenantRepository tenantRepository,
                                                                 RoleRepository roleRepository,
                                                                 UserDetailsService userDetailsService,
                                                                 JwtUtil jwtUtil,
                                                                 PasswordEncoder passwordEncoder) {
        return new CustomOAuth2SuccessHandler(userRepository, tenantRepository, roleRepository, userDetailsService, jwtUtil, passwordEncoder);
    }
}
