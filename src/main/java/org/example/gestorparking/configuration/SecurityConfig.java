package org.example.gestorparking.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws
            Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas (accesibles sin login)
                        .requestMatchers("/", "/registro",
                                "/login", "/fragments/**").permitAll()
                        .requestMatchers("/css/**", "/js/**",
                                "/images/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll() //Solo para desarrollo
                        // Rutas protegidas (requieren autenticación)
                        .requestMatchers("/admin/**", "/profile", "/userProfile").hasAnyRole("USER", "ADMIN")
                        // Cualquier otra ruta requiere autenticación
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login") // Página de login personalizada
                        .loginProcessingUrl("/login") // URL que procesa el login
                        .successHandler(new SavedRequestAwareAuthenticationSuccessHandler()) // Redirige a la página pedida por usuario tras login exitoso
                        .failureUrl("/login?error=true") // Redirige aquí si falla
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL para hacer logout
                        .logoutSuccessUrl("/") // Redirige a / tras logout
                        .permitAll()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**") //Deshabilitar CSRF para H2
                )
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin()) //Permitir H2 console en iframe
                );
        return http.build();
    }


}