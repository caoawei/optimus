package com.optimus.module.account.ao;

import com.optimus.module.account.dal.entity.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账户ao
 */
@FeignClient("user")
@RestController("/account")
public interface AccountAo {

    @RequestMapping("/register")
    UserInfo register();
}
