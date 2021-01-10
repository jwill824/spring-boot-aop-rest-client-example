package io.thingstead.lib.samurai.handler;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import io.thingstead.lib.samurai.annotation.Api;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class SpringAOPJoinPointHandler {
    public static Method getMethodFromJoinPoint(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }

    public static Api getDeclaredAnnotationFromJoinPoint(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = ((MethodSignature) joinPoint.getSignature());
        Class<?> declaringType = signature.getDeclaringType();
        return declaringType.getDeclaredAnnotation(Api.class);
    }

    public static JavaType getJavaTypeFromJoinPoint(ProceedingJoinPoint joinPoint) {
        Type type = getMethodFromJoinPoint(joinPoint).getGenericReturnType();
        ParameterizedType parameterizedType = (ParameterizedType) type;
        TypeFactory typeFactory = new ObjectMapper().getTypeFactory();
        JavaType javaType = typeFactory.constructType(parameterizedType);

        if (parameterizedType.getRawType().equals(List.class)) {
            Type inner = parameterizedType.getActualTypeArguments()[0];
            javaType = typeFactory.constructCollectionType( List.class, (Class<?>) inner);
        }

        return javaType;
    }
}
