package com.optimus.module.account.service.impl;

import com.optimus.module.account.dal.entity.UserInfo;
import com.optimus.module.account.dal.entity.UserInfoExample;
import com.optimus.module.account.dal.mapper.UserInfoMapper;
import com.optimus.module.account.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/7/28.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo selectByUserId(Long userId) {
        UserInfoExample example = new UserInfoExample();
        example.createCriteria().andUserIdEqualTo(userId);

        List<UserInfo> rs = userInfoMapper.selectByExample(example);
        return rs != null && rs.size() > 0 ? rs.get(0) : null;
    }
}
