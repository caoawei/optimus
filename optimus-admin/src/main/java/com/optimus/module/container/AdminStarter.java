package com.optimus.module.container;

import com.optimus.common.ResetApiStarter;
import com.optimus.mvc.config.WebConfig;
import java.lang.invoke.MethodHandles;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

/**
 * Created by caoawei on 2018/7/25.
 */
@SpringBootApplication(scanBasePackages = "com.*")
@EnableFeignClients(basePackages = "com.optimus.module.*.api")
@EnableDiscoveryClient
@EnableAutoConfiguration
@Import(WebConfig.class)
public class AdminStarter {

    public static void main(String[] args) {
        ResetApiStarter.start(MethodHandles.lookup().lookupClass());
    }
}
