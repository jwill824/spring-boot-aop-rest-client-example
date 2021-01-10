package io.thingstead.lib.samurai.handler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public class AnnotationHandler {
    @SuppressWarnings("unchecked")
    public static void alterAnnotationValueJDK8(Class<?> targetClass, Class<? extends Annotation> targetAnnotation, Annotation targetValue) {
        try {
            Method method = Class.class.getDeclaredMethod("annotationData");
            method.setAccessible(true);
            Object annotationData = method.invoke(targetClass);
            Field annotations = annotationData.getClass().getDeclaredField("annotations");
            annotations.setAccessible(true);
            Map<Class<? extends Annotation>, Annotation> map = (Map<Class<? extends Annotation>, Annotation>) annotations.get(annotationData);
            map.put(targetAnnotation, targetValue);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
