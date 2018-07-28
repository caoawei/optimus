package com.optimus.module.account.api;

import com.optimus.module.account.dal.entity.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 账户ao
 */
@FeignClient("user")
public interface AccountApi {

    @RequestMapping("/select")
    UserInfo select();
}
