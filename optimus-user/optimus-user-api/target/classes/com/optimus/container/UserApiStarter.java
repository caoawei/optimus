package com.optimus.container;

import com.optimus.common.ResetApiStarter;
import com.optimus.mvc.config.DataSourceConfig;
import com.optimus.mvc.config.WebConfig;
import java.lang.invoke.MethodHandles;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages="com.*")
@MapperScan(basePackages = "com.optimus.module.*.dal.mapper")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.optimus.module.*.api")
@EnableAutoConfiguration
@Import({DataSourceConfig.class, WebConfig.class})
public class UserApiStarter {
    public static void main(String[] args) throws Exception {
        ResetApiStarter.start(MethodHandles.lookup().lookupClass());
    }
}
