package com.groupseven.hunthub.application.security;

import com.groupseven.hunthub.domain.repository.UserRepository;
import com.groupseven.hunthub.domain.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService TokenService;
    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, java.io.IOException {
        var token = this.RecoverToken(request);
        if (token != null) {
            var subject = TokenService.validateToken(token);
            if (subject != null) {
                UserDetails user = userRepository.findByEmail(subject);
                if (user != null) {
                    System.out.println("User found: " + user.getUsername());
                    System.out.println("Authorities: " + user.getAuthorities());
                    var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else {
                    System.out.println("User not found with email: " + subject);
                }
            } else {
                System.out.println("Token validation failed");
            }
        } else {
            System.out.println("No token found in request");
        }
        filterChain.doFilter(request, response);
    }

    private String RecoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null)
            return null;
        return authHeader.replace("Bearer ", "");
    }
}
