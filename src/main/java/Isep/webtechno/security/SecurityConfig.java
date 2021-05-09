package Isep.webtechno.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
//                .httpBasic()
//                .and()
                .authorizeRequests()
                .antMatchers("/**").permitAll() //allow all requests

                .anyRequest()
                .authenticated()
                ;
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails adminUser = User.builder()
                .username("mat")
                .password(passwordEncoder.encode("pass"))
                .roles("ADMIN")
                .build();
        UserDetails user = User.builder()
                .username("random")
                .password(passwordEncoder.encode("pass"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(adminUser);
    }
}
