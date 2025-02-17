package com.babbangona.commons.library.utils;

import com.babbangona.commons.library.entities.User;
import com.babbangona.commons.library.entities.Role;
import com.babbangona.commons.library.entities.Tenant;
import com.babbangona.commons.library.repo.UserRepository;
import com.babbangona.commons.library.repo.RoleRepository;
import com.babbangona.commons.library.repo.TenantRepository;
import com.babbangona.commons.library.security.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomOAuth2SuccessHandler.class);

    private static final String USER_ROLE_OAUTH2 = "DOMAIN_USER";
    private static final String USER_TENANT_OAUTH2 = "OAUTH2_TENANT";

    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final RoleRepository roleRepository;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    private ObjectMapper marshallJson = new ObjectMapper();

    public CustomOAuth2SuccessHandler(UserRepository userRepository,
                                      TenantRepository tenantRepository,
                                      RoleRepository roleRepository,
                                      UserDetailsService userDetailsService,
                                      JwtUtil jwtUtil,
                                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tenantRepository = tenantRepository;
        this.roleRepository = roleRepository;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // Cast the principal to OAuth2User to access GitHub attributes.
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

        String email = oauth2User.getAttribute("email");
        //String name = oauth2User.getAttribute("name");
        if (email == null) {
            email = oauth2User.getAttribute("login");
        }


        Optional<User> existingUser = userRepository.findByUsername(email);
        User user;
        if (existingUser.isPresent()) {
            user = existingUser.get();
        } else {

            user = new User();
            user.setUsername(email);
            // For OAuth2 users, we can store a generated or default password.
            user.setPassword(passwordEncoder.encode("defaultPassword"));

            Tenant tenant = tenantRepository.findByName(USER_TENANT_OAUTH2)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Tenant ID"));


            Optional<Role> defaultRole = roleRepository.findByName(USER_ROLE_OAUTH2);
            if (defaultRole.isEmpty()) {
                throw new IllegalArgumentException("Invalid Role IDs provided");
            }
            user.setTenant(tenant);
            user.getUserRoles().add(defaultRole.get());

            userRepository.save(user);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String jwt = jwtUtil.generateToken(userDetails);

        // Redirect or respond with the JWT (here, using redirect)
        //Client side logical endpoint can be configured here.
        response.sendRedirect("/api/auth/post-login?token=" + jwt);
    }
}