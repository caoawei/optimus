package com.optimus.container;

import com.optimus.common.ResetApiStarter;
import com.optimus.module.account.ao.AccountAo;
import com.optimus.module.account.dal.entity.UserInfo;
import com.optimus.utils.SpringMvcUtil;
import com.optimus.utils.Utils;
import java.lang.invoke.MethodHandles;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages="com.*")
@EnableAutoConfiguration
@EnableDiscoveryClient
@MapperScan(basePackages = "com.optimus.module.*.dal.mapper")
@RestController
public class UserApiStarter {
    public static void main(String[] args) throws Exception {
        ResetApiStarter.start(MethodHandles.lookup().lookupClass());
    }

    @Autowired
    AccountAo accountAo;

    @RequestMapping("/select")
    public UserInfo select() {
        return accountAo.register();
    }
}
