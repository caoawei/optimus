package com.souche.validation.core.util;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import org.springframework.util.StringUtils;

public class ValidationConfigUtils {

    public static <T> List<T> loadClass(Class<T> type,ClassLoader classLoader) {
        try {
            Enumeration<URL> urls = classLoader != null ? classLoader.getResources("META-INF/validation.properties") : ClassLoader.getSystemResources("META-INF/validation.properties");
            List<String> classNames = new ArrayList<>();

            while(urls.hasMoreElements()) {
                URL url = urls.nextElement();
                Properties properties = new Properties();
                properties.load(url.openStream());
                classNames.addAll(parseValidatorClassNames(properties));
            }

            return createInstance(classNames,type);

        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to load properties from location [META-INF/validator.factories]", e);
        }
    }

    private static <T> List<T> createInstance(List<String> classNames,Class<T> superType) {

        if(classNames.isEmpty()) {
            return null;
        }

        List<T> rs = new ArrayList<>();
        for (String cn : classNames) {
            try {
                Class cl = Class.forName(cn);
                if(superType.isAssignableFrom(cl)) {
                    rs.add((T) cl.newInstance());
                }
            } catch (Throwable e) {
                throw new IllegalArgumentException("Cannot instantiate " + superType + " : " + cn, e);
            }
        }

        return rs;
    }

    private static List<String> parseValidatorClassNames(Properties properties) {
        String cv = properties.getProperty("com.souche.validation.core.Validator");
        String[] arr = cv.split(",");
        if(StringUtils.isEmpty(cv)) {
            return Collections.emptyList();
        }

        if(arr == null || arr.length < 1) {
            return Collections.emptyList();
        }

        List<String> names = new ArrayList<>(arr.length);
        for (String v : arr) {
            names.add(v.trim());
        }

        return names;
    }
}
