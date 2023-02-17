package com.jasper.munselfservice.interceptor;

import com.jasper.munselfservice.controller.BaseController;
import com.jasper.munselfservice.entity.Student;
import com.jasper.munselfservice.config.SpringContext;
import com.jasper.munselfservice.repository.StudentRepository;
import com.jasper.munselfservice.util.CORSUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionInterceptor implements HandlerInterceptor {
    private final RedisTemplate<Integer, String> redisTemplate = SpringContext.getAutowiredRedisTemplate();

    private final StudentRepository studentRepository = (StudentRepository) SpringContext.getAutowireable("studentRepository");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        CORSUtil.removeCORSPolicy(response);

        if (request.getRequestURL().indexOf("login") == -1) {
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
                response.setStatus(404);
                return false;
            }
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
