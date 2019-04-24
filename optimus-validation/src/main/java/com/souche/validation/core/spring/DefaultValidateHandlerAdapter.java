package com.souche.validation.core.spring;

import com.souche.validation.core.RuleDefinition;
import com.souche.validation.core.ValidateHandlerAdapter;
import com.souche.validation.core.ValidationAttribute;
import com.souche.validation.core.Validator;
import com.souche.validation.core.util.ValidationConfigUtils;
import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.ReflectionUtils;

public class DefaultValidateHandlerAdapter implements ValidateHandlerAdapter, ApplicationListener {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private Map<Class<?>, Validator> internalValidatorMap = new LinkedHashMap<>();

    private Map<Class<?>, Validator> extendValidatorMap;

    @Override
    public void validate(ValidationAttribute va, Object[] args) {
        if (args == null || args.length < 1) {
            return;
        }

        for (int index = 0; index < args.length; index++) {
            logger.info("Execute validate on method {} for class {}, {} method parameter", va.getMethodName(), va.getTargetClass().getName(), index);

            invokeValidator(va.getRuleDefinition(index),args[index]);

            invokeValidator(va.getRuleDefinition(args[index].getClass()),args[index]);
        }
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if(ContextRefreshedEvent.class.getName().equals(event.getClass().getName())) {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            // Loading internal implements for Validator
            List<Validator> internalValidator = ValidationConfigUtils.loadClass(Validator.class, classLoader);
            addValidator(internalValidator, internalValidatorMap);

            // Loading extend implements for Validator
            Map<String, Validator> beanMap = ((ContextRefreshedEvent) event).getApplicationContext().getBeansOfType(Validator.class);
            if (!beanMap.isEmpty()) {
                extendValidatorMap = new LinkedHashMap<>();
                addValidator(beanMap.values(), extendValidatorMap);
            }
        }
    }

    private void addValidator(Collection<Validator> internalValidator2, Map<Class<?>, Validator> validatorMap) {
        for (Validator validator : internalValidator2) {
            validatorMap.put(validator.supportAnnotation(), validator);
        }
    }

    private void invokeValidator(RuleDefinition rd, Object source) {
        if(rd == null) {
            return;
        }

        List<Annotation> foundAnnotations = rd.getValidationAnnotationList();
        if(foundAnnotations == null || foundAnnotations.isEmpty()) {
            return;
        }

        for (Annotation annotation : foundAnnotations) {
            Validator validator = internalValidatorMap.get(annotation.annotationType());
            if(validator != null) {
                int type = rd.getType();
                try {

                    if(type == 1) {
                        validator.validate(source,annotation);
                    } else {
                        validator.validate(source,ReflectionUtils.findField(source.getClass(),rd.getName()),annotation);
                    }
                } catch (Exception e) {
                    if(type == 1) {
                        logger.error("the class {} method {} {} arg named {} check error,the error message is: {}",rd.getMethodDeclaringClass().getName(),rd.getMethodName(),rd.index(),rd.getName(),e.getMessage(),e);
                    } else {
                        logger.error("the class {} method {} {} arg field {} check error,the error message is: {}",rd.getMethodDeclaringClass().getName(),rd.getMethodName(),rd.index(),rd.getSource().getName().concat(".").concat(rd.getName()),e.getMessage(),e);
                    }

                    throw e;
                }
            }
        }
    }
}