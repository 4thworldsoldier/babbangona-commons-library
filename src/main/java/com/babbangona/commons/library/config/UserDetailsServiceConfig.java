package com.babbangona.commons.library.config;
import com.babbangona.commons.library.entities.User;
import com.babbangona.commons.library.repo.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
public class UserDetailsServiceConfig {



    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            Optional<User> userOptional = userRepository.findByUsername(username);
            if (!userOptional.isPresent()) {
                throw new UsernameNotFoundException("User not found");
            }
            User user = userOptional.get();
            List<GrantedAuthority> authorities = new ArrayList<>();

            user.getUserRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));

                // Add each privilege as an authority
                if (role.getPrivileges() != null) {
                    role.getPrivileges().forEach(privilege -> {
                        authorities.add(new SimpleGrantedAuthority(privilege.getName()));
                    });
                }
            });

            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    authorities
            );
        };
    }

}

