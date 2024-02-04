package org.recipe.originalyrecipe.config;

import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.recipe.originalyrecipe.models.entity.Utilisateur;
import org.recipe.originalyrecipe.repository.UtilisateurRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(@NotNull HttpSecurity http, UtilisateurRepository utilisateurRepository) throws Exception {
        http
                .authorizeRequests((requests) -> requests
                        .requestMatchers("/utilisateur/add").permitAll()
                        .requestMatchers("/utilisateur/all").permitAll()
                        .requestMatchers("/utilisateur/logout").permitAll()
                        .requestMatchers("/utilisateur/searchByMail").permitAll()
                        .requestMatchers("/utilisateur/login").permitAll()
                        .requestMatchers("/utilisateur/delete").authenticated()

                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/utilisateur/logout")  // Spécifiez l'URL de déconnexion
                        .logoutSuccessUrl("/utilisateur/login")  // Spécifiez l'URL de redirection après déconnexion
                        .invalidateHttpSession(true)  // Invalider la session HTTP
                        .deleteCookies("JSESSIONID")  // Supprimer les cookies, ajustez selon vos besoins
                        .permitAll()
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                        })
                )

                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/utilisateur/add","/utilisateur/login","/utilisateur/logout")); // Ignorer la protection CSRF pour "/utilisateur/add"

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UtilisateurRepository utilisateurRepository) {
        return email -> {
            Utilisateur utilisateur = utilisateurRepository.findByMail(email);
            if (utilisateur == null) {
                throw new UsernameNotFoundException("Utilisateur non trouvé avec l'adresse e-mail : " + email);
            }

            return User.builder()
                    .username(utilisateur.getMail())
                    .password(utilisateur.getMotDePasse())
                    .roles(utilisateur.getRole().getName())  // Vous pouvez ajuster cela selon votre modèle de rôles
                    .build();
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
