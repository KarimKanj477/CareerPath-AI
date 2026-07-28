package com.careerpath.careerpathai.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter,
                          UserDetailsService userDetailsService,
                          CustomAuthenticationEntryPoint authenticationEntryPoint,
                          CustomAccessDeniedHandler accessDeniedHandler) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // Only login and register are truly public on the auth routes
                        .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        // Swagger / OpenAPI docs
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()
                        // Public read-only access to reference data
                        .requestMatchers(HttpMethod.GET, "/api/careers").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/careers/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/careers/search").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/skills").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/skills/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/skills/search").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/roles").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/roles/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/learning-resources").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/learning-resources/{id}").permitAll()
                        // Career skills read is public; writes are admin-only (path is /api/careers/{id}/skills)
                        .requestMatchers(HttpMethod.GET, "/api/careers/*/skills").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/careers/*/skills").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/careers/*/skills/**").hasRole("ADMIN")
                        // Admin-only write access to reference data
                        .requestMatchers(HttpMethod.POST, "/api/careers").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/careers/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/careers/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/skills").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/skills/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/skills/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/roles").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/roles/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/roles/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/learning-resources").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/learning-resources/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/learning-resources/{id}").hasRole("ADMIN")
                        // All other requests require authentication
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
