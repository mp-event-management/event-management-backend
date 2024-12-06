package com.eventmanagement.EventManagementBackend.infrastructure.config;

import com.eventmanagement.EventManagementBackend.usecase.auth.GetUserAuthDetailsUsecase;
import com.eventmanagement.EventManagementBackend.usecase.events.EventsPublicUsecase;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {
    private final GetUserAuthDetailsUsecase getUserAuthDetailsUsecase;
    private final EventsPublicUsecase getEventsUsecase;
    private final JwtConfigProperties jwtConfigProperties;
    private final RsaKeyConfigProperties rsaKeyConfigProperties;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig
            (
                    EventsPublicUsecase getEventsUsecase,
                    JwtConfigProperties jwtConfigProperties,
                    RsaKeyConfigProperties rsaKeyConfigProperties,
                    PasswordEncoder passwordEncoder,
                    GetUserAuthDetailsUsecase getUserAuthDetailsUsecase
            ) {
        this.getEventsUsecase = getEventsUsecase;
        this.jwtConfigProperties = jwtConfigProperties;
        this.rsaKeyConfigProperties = rsaKeyConfigProperties;
        this.passwordEncoder = passwordEncoder;
        this.getUserAuthDetailsUsecase = getUserAuthDetailsUsecase;
    }

    @Bean
    public AuthenticationManager authManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(getUserAuthDetailsUsecase);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(new CorsConfigurationSourceImpl()))
                .authorizeHttpRequests(auth -> auth
                        // Define public routes
                        .requestMatchers("/error/**").permitAll()
                        .requestMatchers("/api/v1/auth/login").permitAll()
                        .requestMatchers("/api/v1/users/register").permitAll()
                        .anyRequest().permitAll())
                .oauth2ResourceServer(oauth2 -> {
                    oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder()));
                        }
                )
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKey originalKey = new SecretKeySpec(jwtConfigProperties.getSecret().getBytes(), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(originalKey).build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        SecretKey key = new SecretKeySpec(jwtConfigProperties.getSecret().getBytes(), "HmacSHA256");
        JWKSource<SecurityContext> immutableSecret = new ImmutableSecret<>(key);
        return new NimbusJwtEncoder(immutableSecret);
    }

//    // Using Rsa secret key
//    @Bean
//    public JwtDecoder jwtDecoder() {
//        return NimbusJwtDecoder.withPublicKey(rsaKeyConfigProperties.publicKey()).build();
//    }
//
//    @Bean
//    JwtEncoder jwtEncoder() {
//        JWK jwk = new RSAKey.Builder(rsaKeyConfigProperties.publicKey()).privateKey(rsaKeyConfigProperties.privateKey()).build();
//        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
//        return new NimbusJwtEncoder(jwks);
//    }
}

