package Isep.webtechno.security.auth.jwt;

import Isep.webtechno.model.entity.User;
import Isep.webtechno.model.repo.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.List;

public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {

            final String requestTokenHeader = request.getHeader(JwtUtils.headerAuthorization);
            System.out.println("Auth : " + requestTokenHeader);

            if(request.getHeader(JwtUtils.headerAuthorization) != null && requestTokenHeader.startsWith(JwtUtils.headerAuthorizationPrefix)) {
                String requestJwt = requestTokenHeader.substring(7);
                JwtParser build = Jwts.parserBuilder().setSigningKey(JwtUtils.secretKey.getBytes(StandardCharsets.UTF_8)).build();
                Claims body = build.parseClaimsJws(requestJwt).getBody();

                String username = body.getSubject();
                System.out.println("Connected as " + username);


                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                filterChain.doFilter(request, response);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        filterChain.doFilter(request, response);
    }

}
