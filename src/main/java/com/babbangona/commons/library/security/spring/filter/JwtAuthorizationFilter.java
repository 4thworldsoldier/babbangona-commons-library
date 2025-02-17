package com.babbangona.commons.library.security.spring.filter;

import com.babbangona.commons.library.dto.response.BaseResponse;
import com.babbangona.commons.library.security.spring.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private ObjectMapper marshallJson = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService userDetailsService ) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/api/auth/")
                || requestURI.startsWith("/login/**")
                || requestURI.startsWith( "/oauth2/**")
                || requestURI.startsWith(  "/post-login")
                || requestURI.startsWith(  "/favicon.ico")) {
            chain.doFilter(request, response);
            return; // UNSECURED PATHS
        }

        BaseResponse resp = new BaseResponse();
        String header = request.getHeader("Authorization");

        if (Objects.isNull(header) || !header.startsWith("Bearer ")) {
            resp.setDescription("Invalid Authentication Header!!");
            response.setContentType("application/json");
            response.getWriter().write(marshallJson.writeValueAsString(resp));
            response.getWriter().flush();
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        if (authentication == null) {
            resp.setDescription("Invalid JWT token detected!!");
            response.setContentType("application/json");
            response.getWriter().write(marshallJson.writeValueAsString(resp));
            response.getWriter().flush();
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        UsernamePasswordAuthenticationToken authentication = null;
        if (Objects.nonNull(token)) {
            try {
                String username = jwtUtil.extractUsernameFromToken(token.replace("Bearer ", ""));
                if (Objects.nonNull(username)) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (jwtUtil.validateToken(token.replace("Bearer ", ""), userDetails)) {
                        authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    }
                }
            } catch (Exception e) {
                logger.error("JWT TOKEN VALIDATION FAILED : ", e.getMessage());
                return null;
            }
        }
        return authentication;
    }
}
