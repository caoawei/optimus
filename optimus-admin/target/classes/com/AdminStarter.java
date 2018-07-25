package com;

import com.optimus.common.ResetApiStarter;
import com.optimus.module.test.AccountTest;
import com.optimus.utils.SpringMvcUtil;
import com.optimus.utils.Utils;
import java.lang.invoke.MethodHandles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by caoawei on 2018/7/25.
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@RestController
public class AdminStarter {


    public static void main(String[] args) {
        ResetApiStarter.start(MethodHandles.lookup().lookupClass());

        AccountTest accountTest = SpringMvcUtil.getBean(AccountTest.class);
        System.out.println(Utils.toJson(accountTest.selectUserInfo()));
    }
}
