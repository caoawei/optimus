package com.optimus.module.test.web.rpc;

import com.optimus.module.account.api.AccountApi;
import com.optimus.module.account.dal.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by  on 2018/7/27.
 */
@RestController
@RequestMapping("/account/user")
public class UserRpc {

    @Autowired
    private AccountApi accountApi;

    @RequestMapping("/select.json")
    public UserInfo select() {
        return accountApi.select();
    }
}
