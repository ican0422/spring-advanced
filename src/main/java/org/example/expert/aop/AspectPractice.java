package org.example.expert.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.expert.config.AuthUserArgumentResolver;
import org.example.expert.domain.common.annotation.Auth;
import org.example.expert.domain.common.dto.AuthUser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Slf4j
@Aspect
public class AspectPractice {

    @Pointcut("@annotation(org.example.expert.aop.annotation.ApiLogging)")
    private void apiLoggingAnnotation(){}

    @Around("apiLoggingAnnotation()")
    public Object adviceAnnotation(ProceedingJoinPoint joinPoint) throws Throwable{
        HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String url = httpRequest.getRequestURL().toString();
        Long userId = (Long) httpRequest.getAttribute("userId");
        // 측정 시작
        LocalDateTime startTime = LocalDateTime.now();

        try {
            Object result = joinPoint.proceed();
            return result;
        }finally {
            log.info("::: User ID {}", userId);
            log.info("::: executionTime {}ms", startTime);
            log.info("::: URL {}",url);
        }
    }
}
