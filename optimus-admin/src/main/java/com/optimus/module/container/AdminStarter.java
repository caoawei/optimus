package com.optimus.module.container;

import com.optimus.common.ResetApiStarter;
import java.lang.invoke.MethodHandles;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created by caoawei on 2018/7/25.
 */
@SpringBootApplication(scanBasePackages = "com.*")
@EnableFeignClients
@EnableDiscoveryClient
public class AdminStarter {

    public static void main(String[] args) {
        ResetApiStarter.start(MethodHandles.lookup().lookupClass());
    }
}
