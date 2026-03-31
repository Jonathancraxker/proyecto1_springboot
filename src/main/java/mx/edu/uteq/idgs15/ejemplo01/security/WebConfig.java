package mx.edu.uteq.idgs15.ejemplo01.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{

    public void addViewControllers(ViewControllerRegistry registro){
        
        registro.addViewController("/login");
    }


@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
    .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests((authz) -> {
        authz.requestMatchers("/", "/oferta-educativa", "/directorio", "/mision", "/sitemap", "/login", "/images/**", "/css/**", "/adm", "/other", "/email/**").permitAll()
        // ADMIN se encarga de todo
        // CORDINADOR se encarga de oferta educativa y divisiones
        // RECTOR se encarga de misiones y directorio
        // EDITOR solo puede editar pero no agregar ni borrar
        // USER solo puede ver la información pública como usuario final pero no tiene acceso a la consola de administración
        .requestMatchers("/consola/divisiones").hasAnyRole("ADMIN", "CORDINADOR", "EDITOR")
        .requestMatchers("/consola/divisiones/add").hasAnyRole("ADMIN", "CORDINADOR")
        .requestMatchers("/consola/misiones").hasAnyRole("ADMIN", "RECTOR", "EDITOR")
        .requestMatchers("/consola/misiones/add").hasAnyRole("ADMIN", "RECTOR")
        .requestMatchers("/consola/oferta-educativa").hasAnyRole("ADMIN", "CORDINADOR", "EDITOR")
        .requestMatchers("/consola/oferta-educativa/add").hasAnyRole("ADMIN", "CORDINADOR")
        .requestMatchers("/consola/directorio").hasAnyRole("ADMIN", "RECTOR", "EDITOR")
        .requestMatchers("/consola/directorio/add").hasAnyRole("ADMIN", "RECTOR")
            .anyRequest().authenticated();
        }
        )
        .formLogin((form) -> form.loginPage("/login").permitAll()
        )
        .logout((logout) -> logout.permitAll());
        
    return http.build();
    }

}
