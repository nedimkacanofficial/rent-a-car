package com.rentacar.security.jwt;

import com.rentacar.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Description of UpdatePasswordRequestDTO.
 *
 * @author github.com/nedimkacanofficial
 * @version 1.0
 * @package com.rentacar.security.jwt
 * @since 28/11/2023
 */
@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtUtilsService jwtUtilsService;

    private final UserDetailsService userDetailsService;

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && this.jwtUtilsService.validateJwtToken(jwt)) {
                Long id = this.jwtUtilsService.getIdFromJwtToken(jwt);
                this.userRepository.findById(id).ifPresent(user -> {
                    request.setAttribute("id", user.getId());
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(user.getEmail());
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("User successfully authenticated. User ID: {}", id);
                });
            }
        } catch (Exception e) {
            log.error("User Authentication error: {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        boolean shouldNotFilter = antPathMatcher.match("/register", request.getServletPath()) || antPathMatcher.match("/login", request.getServletPath());
        if (shouldNotFilter) {
            log.info("Request path matched exclusion path. Skipping JWT filter.");
        }
        return shouldNotFilter;
    }
}
