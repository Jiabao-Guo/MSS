package com.jasper.munselfservice.interceptor;

import com.jasper.munselfservice.controller.BaseController;
import com.jasper.munselfservice.entity.Student;
import com.jasper.munselfservice.config.SpringContext;
import com.jasper.munselfservice.repository.StudentRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// @Component
public class SessionInterceptor implements HandlerInterceptor {
    private final RedisTemplate<Integer, String> redisTemplate = SpringContext.getAutowiredRedisTemplate();

    private final StudentRepository studentRepository = (StudentRepository) SpringContext.getAutowireable("studentRepository");

    private final List<String> filteringMethods = new ArrayList<>()
    {{
        add("POST");
        add("PUT");
        add("DELETE");
        add("PATCH");
    }};

    private final List<String> allowedUrls = new ArrayList<>()
    {{
        add("login");
        add("task");
    }};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        setResponseHeaders(response);

        String requestedUrl = request.getRequestURL().toString().toLowerCase(Locale.ROOT);
        String requestedMethod = request.getMethod().toUpperCase(Locale.ROOT);

        if (!filteringMethods.contains(requestedMethod)) {
            return true;
        }

        if (allowedUrls.stream().anyMatch(requestedUrl::contains)) {
            return true;
        }

        try {
            Integer studentNumber = Integer.parseInt(request.getHeader("X-MSS-Student-Number"));
            String sessionId = request.getHeader("X-MSS-Session-Id");
            Student student = BaseController.getStudentFromSessionId(redisTemplate, studentRepository, studentNumber, sessionId);
            if (student == null) {
                response.setStatus(404);
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setStatus(404);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        setResponseHeaders(response);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        setResponseHeaders(response);
    }

    private void setResponseHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Credentials", "false");
        response.setIntHeader("Access-Control-Max-Age", 3600000);
    }
}
