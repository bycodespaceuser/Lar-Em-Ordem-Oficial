package br.com.laremordem.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ConsoleConfig {
    // Protege somente o console do banco, sem login no site.
    @Bean
    SecurityFilterChain seguranca(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console", "/h2-console/**").authenticated()
                .anyRequest().permitAll());
        http.httpBasic(basic -> {});
        http.csrf(csrf -> csrf.disable()); // Aplicação de demonstração sem contas de usuários.
        http.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));
        return http.build();
    }

    @Bean
    UserDetailsService usuarioConsole(
            @Value("${CONSOLE_USER:demo}") String usuario,
            @Value("${CONSOLE_PASSWORD:demo-local-123}") String senha,
            @Value("${H2_CONSOLE_REMOTE:false}") boolean remoto,
            @Value("${H2_CONSOLE_ENABLED:true}") boolean habilitado) {
        if (remoto && habilitado && (senha.equals("demo-local-123") || senha.length() < 16)) {
            throw new IllegalStateException("Defina CONSOLE_PASSWORD com pelo menos 16 caracteres.");
        }
        return new InMemoryUserDetailsManager(User.withUsername(usuario)
                .password("{bcrypt}" + new BCryptPasswordEncoder().encode(senha)).roles("CONSOLE").build());
    }
}
