package com.optimus.module.account.ao.impl;

import com.optimus.module.account.ao.AccountAo;
import com.optimus.module.account.dal.entity.UserInfo;
import java.util.Date;
import org.springframework.stereotype.Service;

/**
 * Created by caoawei on 2018/7/25.
 */
@Service
public class AccountAoImpl implements AccountAo {

    @Override
    public UserInfo register() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1810000000L);
        userInfo.setWechatId("djfaldghladkhglahdahweiop");
        userInfo.setGmtModified(new Date());
        userInfo.setGmtCreated(new Date());
        userInfo.setMobile(13093752195L);
        return userInfo;
    }
}
