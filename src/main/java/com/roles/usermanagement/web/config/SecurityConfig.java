package com.roles.usermanagement.web.config;

import com.roles.usermanagement.domain.service.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

/**
 * Configuración de seguridad de la aplicación. Esta clase configura la seguridad de la aplicación
 * estableciendo permisos de acceso para diferentes rutas y definiendo el comportamiento de autenticación.
 */
@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    private final JwtFilter jwtFilter;

    @Autowired
    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    /**
     * Define la configuración de seguridad para las rutas y restricciones de acceso.
     *
     * @param http El objeto HttpSecurity para configurar la seguridad.
     * @return El filtro de seguridad configurado para la aplicación.
     * @throws Exception Si hay errores al configurar la seguridad.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(customRequest -> customRequest
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/customers/**").hasAnyRole(UserRoles.Role.ADMIN.name(), UserRoles.Role.CUSTOMER.name())
                .requestMatchers(HttpMethod.GET, "/api/pizzas/**").hasAnyRole(UserRoles.Role.ADMIN.name(), UserRoles.Role.CUSTOMER.name())
                .requestMatchers(HttpMethod.POST, "/api/pizzas/**").hasRole(UserRoles.Role.ADMIN.name())
                .requestMatchers(HttpMethod.PUT).hasRole(UserRoles.Role.ADMIN.name())
                .requestMatchers("/api/orders/random").hasAuthority(UserRoles.Authority.RANDOM_ORDER.name())
                .requestMatchers("/api/orders/**").hasRole(UserRoles.Role.ADMIN.name())
                .requestMatchers(HttpMethod.GET,"/api/**").permitAll()
                .requestMatchers(HttpMethod.PUT).denyAll()
                .anyRequest().authenticated())
            .csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    /**
     * Define el administrador de autenticación personalizado utilizado para la autenticación de usuarios.
     *
     * @param configuration La configuración de autenticación.
     * @return El administrador de autenticación personalizado.
     * @throws Exception Si hay errores al obtener el administrador de autenticación.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Devuelve el codificador de contraseñas utilizado para el almacenamiento seguro de contraseñas.
     *
     * @return El codificador de contraseñas utilizado para encriptar y comparar contraseñas.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
