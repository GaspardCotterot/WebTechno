package Isep.webtechno.security.auth.jwt;

import Isep.webtechno.model.entity.User;
import Isep.webtechno.model.repo.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtAuthCredentialsFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public JwtAuthCredentialsFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
//        System.out.println("User : " + username + " - Password : " + password);
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @SneakyThrows
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .signWith(Keys.hmacShaKeyFor(JwtUtils.secretKey.getBytes(StandardCharsets.UTF_8)))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + (1000 * 60 * 60 * 24 * 2)))//2 days
                .compact();

        System.out.println("token generated : " + token);

        response.addHeader(JwtUtils.headerAuthorization, JwtUtils.headerAuthorizationPrefix + token);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONObject object = new JSONObject();
        object.put("token", JwtUtils.headerAuthorizationPrefix + token);

        User user = userRepository.findByMail(authResult.getName()).orElseThrow();
        object.put("user", user.getBasicInfos());
        System.out.println("réponse " + object);

        PrintWriter writer = response.getWriter();
        writer.write(object.toString());


    }
}
