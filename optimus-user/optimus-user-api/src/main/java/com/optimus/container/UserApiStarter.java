package com.optimus.container;

import com.optimus.common.ResetApiStarter;
import java.lang.invoke.MethodHandles;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages="com.*")
@ServletComponentScan(basePackages = "com.*")
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableCircuitBreaker
@MapperScan(basePackages = "com.*")
public class UserApiStarter {
    public static void main(String[] args) throws Exception {
        ResetApiStarter.start(MethodHandles.lookup().lookupClass());
    }
}
