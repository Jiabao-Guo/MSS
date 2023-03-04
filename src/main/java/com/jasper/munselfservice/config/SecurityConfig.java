package com.jasper.munselfservice.config;

import com.jasper.munselfservice.v1.constants.RoleType;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;
import java.util.logging.Logger;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${jwt.public-key}")
    RSAPublicKey jwtPublicKey;

    @Value("${jwt.private-key}")
    RSAPrivateKey jwtPrivateKey;

    public static Map<String, RoleType> RoleMap = Map.of(
        "/api/v1/student", RoleType.ADMINISTRATOR,
        "/api/v1/instructor", RoleType.ADMINISTRATOR,
        "/api/v1/user", RoleType.ADMINISTRATOR
    );

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        Logger.getLogger("SecurityConfig")
            .info("Security config activated.");

        http.csrf().disable()
            .cors().disable()
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
            .sessionManagement((s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)))
            .exceptionHandling((exceptions) -> exceptions
                .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                .accessDeniedHandler(new BearerTokenAccessDeniedHandler()))
            .authorizeHttpRequests()
            .requestMatchers("/").permitAll()
            .requestMatchers("/api/v1/login").permitAll()
            .requestMatchers("/api/v1/renew").permitAll()
            .requestMatchers("/api/v1/test").permitAll()
            .anyRequest().authenticated();
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsDisabler() {
        Logger.getLogger("SecurityConfig")
            .info("CORS disabled.");

        CorsConfiguration config = new CorsConfiguration();

        // Possibly...
        config.applyPermitDefaultValues();

        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.jwtPublicKey).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.jwtPublicKey).privateKey(this.jwtPrivateKey).build();
        return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(jwk)));
    }
}
