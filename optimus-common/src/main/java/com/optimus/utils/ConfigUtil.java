package com.optimus.utils;

import com.optimus.common.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigUtil {

    private static final Logger logger = LoggerFactory.getLogger(ConfigUtil.class);

    private static String[] configPath = {"application.properties","config.properties"};

    private static Map<String,String> localMap = new ConcurrentHashMap<>();
    private static Map<String,String> remoteMap = new ConcurrentHashMap<>();

    static {
        for (String resourceName : configPath){
            loadPropertisFile(resourceName);
        }
    }

    public static Map<String,String> getConfigLike(String propertyPrefix){
        if(Utils.isEmpty(propertyPrefix)){
            return Collections.emptyMap();
        }
        Map<String,String> rs = new LinkedHashMap<>();
        for (Map.Entry<String,String> entry : isRemoteConfig() ? remoteMap.entrySet() : localMap.entrySet()){
            if(entry.getKey().startsWith(propertyPrefix.trim())){
                rs.put(entry.getKey(),entry.getValue());
            }
        }
        return rs;
    }
    public static String getConfig(String property){
        return isRemoteConfig() ? remoteMap.get(property) : localMap.get(property);
    }

    public static Integer getIntegerConfig(String property){
        String val = isRemoteConfig() ? remoteMap.get(property) : localMap.get(property);
        return Utils.isEmpty(val) ? 0 : Integer.valueOf(val);
    }

    public static Long getLongConfig(String property){
        String val = isRemoteConfig() ? remoteMap.get(property) : localMap.get(property);
        return Utils.isEmpty(val) ? 0L : Long.valueOf(val);
    }

    public static String getConfig(String property,String defaultValue){
        String val = isRemoteConfig() ? remoteMap.get(property) : localMap.get(property);
        return val == null ? defaultValue : val;
    }

    public static boolean isDebug(){
        String val = localMap.get("isDebug");
        return val != null && Boolean.valueOf(val);
    }

    public static boolean isRemoteConfig(){
        String val = localMap.get("enable.remote.config");
        return val != null && Boolean.valueOf(val);
    }

    public static String applicationName(){
        if(isRemoteConfig()){
            return remoteMap.get("spring.application.name");
        }
        return localMap.get("spring.application.name");
    }

    public static void loadXmlFile(String resourceName){

    }

    public static void loadPropertisFile(String resourceName){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if(classLoader == null) {
            classLoader = ConfigUtil.class.getClassLoader();
        }

        try {
            Enumeration<URL> urls = classLoader != null? classLoader.getResources(resourceName):ClassLoader.getSystemResources(resourceName);
            Properties props = new Properties();

            if(urls == null || !urls.hasMoreElements()){
                logger.info("配置文件{}不存在",resourceName);
                throw new BizException(resourceName+" not found");
            }

            while(urls.hasMoreElements()) {
                URL url = (URL)urls.nextElement();
                URLConnection con = url.openConnection();
                InputStream is = con.getInputStream();

                try {
                    if(resourceName.endsWith(".properties")) {
                        props.load(is);
                    }
                } finally {
                    is.close();
                }
            }

            if(props.size() > 0){
                for (Map.Entry entry : props.entrySet()){
                    localMap.put(entry.getKey().toString(),entry.getValue().toString());
                }
            }

        } catch (Exception e){
            logger.error("加载配置文件失败::文件:{}::ERROR:{}",resourceName,e.getMessage(),e);
            throw new BizException(e);
        }
    }

    public static void updateConfig(Map<String,String> newConfig){
        synchronized (logger){
            ConfigUtil.remoteMap.clear();
            ConfigUtil.remoteMap.putAll(newConfig);
        }
    }

    private static void loadAllFile(String resourceName){
        if(resourceName == null || resourceName.length() == 0){
            throw new BizException("配置文件名不能为空");
        }

        if(resourceName.endsWith(".properties")){
            loadPropertisFile(resourceName);
        }else if(resourceName.endsWith(".xml")){
            loadXmlFile(resourceName);
        }
    }
}
