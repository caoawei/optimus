package com.optimus.common;

import com.optimus.utils.SpringMvcUtil;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class ResetApiStarter {

    public static void start(Class<?> bootClass){
        SpringApplication springApplication = new SpringApplication(bootClass);
        springApplication.setBannerMode(Banner.Mode.OFF);
        springApplication.setWebEnvironment(true);
        ConfigurableApplicationContext applicationContext = springApplication.run();
        SpringMvcUtil.setApplicationContext(applicationContext);
    }
}
