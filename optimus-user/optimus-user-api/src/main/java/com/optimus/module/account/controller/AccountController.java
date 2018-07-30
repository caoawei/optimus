package com.optimus.module.account.controller;

import com.optimus.module.account.api.AccountApi;
import com.optimus.module.account.dal.entity.UserInfo;
import com.optimus.module.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by caoawei on 2018/7/28.
 */
@RestController
public class AccountController implements AccountApi{

    @Autowired
    private UserService userService;

    public UserInfo select() {
        return userService.selectByUserId(1810000000L);
    }
}
