package com.optimus.module.test;

import com.optimus.module.account.ao.AccountAo;
import com.optimus.module.account.dal.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by caoawei on 2018/7/25.
 */
@RestController("/account")
public class AccountTest {

    @Autowired
    private AccountAo accountAo;

    @RequestMapping("/select.json")
    public UserInfo selectUserInfo() {
        return accountAo.register();
    }
}
