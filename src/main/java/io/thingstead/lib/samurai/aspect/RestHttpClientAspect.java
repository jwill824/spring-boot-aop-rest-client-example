package io.thingstead.lib.samurai.aspect;

import com.fasterxml.jackson.databind.JavaType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import io.thingstead.lib.samurai.annotation.Api;
import io.thingstead.lib.samurai.annotation.GET;
import io.thingstead.lib.samurai.controller.RestHttpClientController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import static io.thingstead.lib.samurai.handler.SpringAOPJoinPointHandler.getDeclaredAnnotationFromJoinPoint;
import static io.thingstead.lib.samurai.handler.SpringAOPJoinPointHandler.getJavaTypeFromJoinPoint;
import static io.thingstead.lib.samurai.handler.SpringAOPJoinPointHandler.getMethodFromJoinPoint;

@Aspect
@Component
@EnableAspectJAutoProxy
public class RestHttpClientAspect {
    @Autowired
    private RestHttpClientController controller;

    @Around("execution (* io.thingstead..api..*(..))")
    public <G> G invokeGet(ProceedingJoinPoint joinPoint) {
        try {
            JavaType javaType = getJavaTypeFromJoinPoint(joinPoint);
            String path = getMethodFromJoinPoint(joinPoint).getAnnotation(GET.class).value();
            Api annotation = getDeclaredAnnotationFromJoinPoint(joinPoint);
            Object[] parameters = joinPoint.getArgs();
            return controller.get(javaType, path, annotation, parameters);
        } catch (Exception e) {
            // TODO: Put ERROR logging here
            throw new RuntimeException(e);
        }
    }

    @Around("@annotation(POST)")
    public <G> G invokePost(ProceedingJoinPoint joinPoint) {
        try {
            return null;
        } catch (Exception e) {
            // TODO: Put ERROR logging here
            throw new RuntimeException(e);
        }
    }
}
