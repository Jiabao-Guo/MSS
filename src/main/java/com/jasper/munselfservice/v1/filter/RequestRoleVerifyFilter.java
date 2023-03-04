package com.jasper.munselfservice.v1.filter;

import com.jasper.munselfservice.config.SecurityConfig;
import com.jasper.munselfservice.util.NumericUtil;
import com.jasper.munselfservice.v1.constants.RoleType;
import jakarta.annotation.Resource;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class RequestRoleVerifyFilter implements Filter {
    @Resource
    JwtDecoder jwtDecoder;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // true: allow, false: deny
        Function<Void, Boolean> procedure = (dummy) -> {
            if (!(servletRequest instanceof HttpServletRequest request)) {
                return true;
            }

            boolean invalidAuth = false;
            Jwt jwt = null;

            HttpServletResponse response = (HttpServletResponse) servletResponse;
            String header = request.getHeader("Authorization");
            if (header == null || !header.startsWith("Bearer ")) {
                invalidAuth = true;
            }

            if (!invalidAuth) {
                jwt = jwtDecoder.decode(header.split(" ")[1]);
                // Privileged user?
                if (NumericUtil.parseBooleanOrDefault(jwt.getClaimAsString("privileged"), false)) {
                    return true;
                }
            }

            for (Map.Entry<String, RoleType> pair : SecurityConfig.RoleMap.entrySet()) {
                if (!request.getRequestURL().toString().contains(pair.getKey())) {
                    continue;
                }

                if (invalidAuth) {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    return false;
                }

                if (!jwt.getClaimAsString("roles").contains(pair.getValue().getDescription())) {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    return false;
                }
            }
            return true;
        };

        if (procedure.apply(null)) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
