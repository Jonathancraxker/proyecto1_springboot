package mx.edu.uteq.idgs15.ejemplo01.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests((authz) -> {
        authz.requestMatchers("/", "/oferta-educativa", "/login", "/adm", "/other").permitAll()
        .requestMatchers("/consola/divisiones").hasAnyRole("ADMIN", "CORDINADOR")
        .requestMatchers("/consola/oferta-educativa", "/consola/oferta-educativa/add").hasRole("ADMIN")
        
            .anyRequest().authenticated();
            //entrar a consoladivisiones el cordinador, admin a todas
            //meter usuario y dar permisos autorizaciones
        }
        )
        .formLogin((form) -> form.permitAll()
        )
        .logout((logout) -> logout.permitAll());
        
    return http.build();
    }

}
