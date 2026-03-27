package mx.edu.uteq.idgs15.ejemplo01.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() throws Exception {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user")
               .password(passwordEncoder().encode("user"))
               .roles("USER")
               .build());
        manager.createUser(User.withUsername("cordinador")
               .password(passwordEncoder().encode("cordinador"))
               .roles("CORDINADOR")
               .build());
        manager.createUser(User.withUsername("admin")
               .password(passwordEncoder().encode("admin"))
               .roles("ADMIN", "USER")
               .build());
        manager.createUser(User.withUsername("rector")
               .password(passwordEncoder().encode("rector"))
               .roles("RECTOR")
               .build());
        manager.createUser(User.withUsername("editor")
               .password(passwordEncoder().encode("editor"))
               .roles("EDITOR")
               .build());
        return manager;
    }
}