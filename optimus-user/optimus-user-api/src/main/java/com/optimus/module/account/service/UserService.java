package com.optimus.module.account.service;

import com.optimus.module.account.dal.entity.UserInfo;

/**
 * Created by caoawei on 2018/7/28.
 */
public interface UserService {

    UserInfo selectByUserId(Long userId);

}
