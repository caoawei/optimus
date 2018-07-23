package com.optimus.module.account.service.impl;

import com.optimus.common.exception.BizException;
import com.optimus.module.account.dal.entity.User;
import com.optimus.module.account.dal.entity.UserExample;
import com.optimus.module.account.dal.entity.UserInfo;
import com.optimus.module.account.dal.entity.UserInfoExample;
import com.optimus.module.account.dal.mapper.UserInfoMapper;
import com.optimus.module.account.dal.mapper.UserMapper;
import com.optimus.module.account.service.UserInfoService;
import com.optimus.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 用户服务
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public int save(User user) {
        Assert.notNull(user,"参数为空");
        Assert.notNull(user.getMobile(),"手机号为空");

        if (user.getStatus() == null) {
            user.setStatus(0);
        }
        if(user.getGmtCreated() == null) {
            user.setGmtCreated(new Date());
        }
        if(user.getGmtModified() == null) {
            user.setGmtModified(new Date());
        }

        int count = userMapper.insertSelective(user);
        if(count <= 0) {
            throw new BizException("新增用户失败");
        }

        UserInfo userInfo = initUserInfo(user);
        return userInfoMapper.insertSelective(userInfo);
    }

    @Override
    public void deleteByUserId(Long userId) {
        UserInfoExample example = createExample();
        example.createCriteria().andUserIdEqualTo(userId);
        userInfoMapper.deleteByExample(example);
    }

    @Override
    public void deleteById(Long id) {
        userInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByMobile(Long mobile) {
        UserInfoExample example = createExample();
        example.createCriteria().andMobileEqualTo(mobile);
        userInfoMapper.deleteByExample(example);
    }

    @Override
    public void deleteByIdNumber(String idNumber) {
        UserInfoExample example = createExample();
        example.createCriteria().andIdNumberEqualTo(idNumber);
        userInfoMapper.deleteByExample(example);
    }

    @Override
    @Transactional
    public int update(UserInfo userInfo) {
        Assert.notNull(userInfo,"参数为空");
        UserInfo oldInfo;
        if(userInfo.getId() != null){
            oldInfo = selectById(userInfo.getId());
        } else if(userInfo.getUserId() != null){
            oldInfo = selectByUserId(userInfo.getUserId());
        } else {
            throw new BizException("参数错误:id和userId至少一个需要传值");
        }

        // 未查询到用户信息,更新失败
        if(oldInfo == null) {
            return 0;
        }

        // 手机号修改
        boolean mobileFlag = userInfo.getMobile() != null && !Objects.equals(userInfo.getMobile(),oldInfo.getMobile());
        if(mobileFlag) {
            UserInfo _userInfo = selectByMobile(userInfo.getMobile());
            if(_userInfo != null){
                throw new BizException("无法修改数据:该手机号已被注册");
            }
        }

        // 修改身份证号或进行实名认证
        boolean idNumberFlag = Utils.isNotEmpty(userInfo.getIdNumber()) && !Objects.equals(oldInfo.getIdNumber(),userInfo.getIdNumber());
        if(idNumberFlag) {
            UserInfo _userInfo = selectByIdNumber(userInfo.getIdNumber());
            if(_userInfo != null){
                throw new BizException("无法修改数据:身份证号已绑定其他账号");
            }
        }

        if(mobileFlag || idNumberFlag) {
            User user = userMapper.selectByPrimaryKey(userInfo.getUserId());
            if(user == null){
                throw new BizException("参数错误:用户不能存在");
            }

            user.setMobile(mobileFlag ? user.getMobile() : user.getMobile());
            user.setStatus(idNumberFlag ? 1 : user.getStatus());
            user.setGmtModified(new Date());
            userMapper.updateByPrimaryKeySelective(user);

            // 状态与 t_user记录保持一致
            userInfo.setStatus(user.getStatus());
        }
        userInfo.setGmtModified(new Date());
        userInfo.setId(oldInfo.getId());
        return userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

    @Override
    public UserInfo selectById(Long id) {
        return id == null ? null : userInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public UserInfo selectByUserId(Long userId) {
        if(userId == null) {
            return null;
        }
        UserInfoExample example = createExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<UserInfo> data = userInfoMapper.selectByExample(example);
        return Utils.isNotEmpty(data) ? data.get(0) : null;
    }

    @Override
    public UserInfo selectByMobile(Long mobile) {
        if(mobile == null) {
            return null;
        }
        UserInfoExample example = createExample();
        example.createCriteria().andMobileEqualTo(mobile);
        List<UserInfo> data = userInfoMapper.selectByExample(example);
        return Utils.isNotEmpty(data) ? data.get(0) : null;
    }

    @Override
    public UserInfo selectByIdNumber(String idNumber) {
        if(idNumber == null) {
            return null;
        }
        UserInfoExample example = createExample();
        example.createCriteria().andIdNumberEqualTo(idNumber);
        List<UserInfo> data = userInfoMapper.selectByExample(example);
        return Utils.isNotEmpty(data) ? data.get(0) : null;
    }

    @Override
    @Transactional
    public void bindWechat(String openId,Long userId) {
        Assert.hasText(openId,"用户openId为空");
        Assert.notNull(userId,"用户id为空");
        UserExample userExample = new UserExample();
        userExample.createCriteria().andWechatIdEqualTo(openId);
        List<User> users = userMapper.selectByExample(userExample);
        if (users != null && users.size() > 0 && !users.get(0).getId().equals(userId)) {
            throw new BizException("您的账号已绑定其他微信号,如需修改请联系客服");
        } else if(users != null && users.size() > 0){
            return;
        }

        User user = userMapper.selectByPrimaryKey(userId);
        user.setWechatId(openId);
        user.setGmtModified(new Date());
        userMapper.updateByPrimaryKeySelective(user);

        UserInfo userInfo = selectByUserId(userId);
        userInfo.setWechatId(openId);
        userInfo.setGmtModified(new Date());
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

    @Override
    @Transactional
    public void unbindWechat(String openId) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andWechatIdEqualTo(openId);
        List<User> users = userMapper.selectByExample(userExample);
        if(users != null && users.size() > 0) {
            User user = users.get(0);
            user.setGmtModified(new Date());
            user.setWechatId(null);
            userMapper.updateByPrimaryKey(user);

            UserInfo userInfo = selectByUserId(user.getId());
            userInfo.setGmtModified(new Date());
            userInfo.setWechatId(null);
            userInfoMapper.updateByPrimaryKey(userInfo);
        }
    }

    private UserInfoExample createExample() {
        return new UserInfoExample();
    }

    private UserInfo initUserInfo(User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setMobile(user.getMobile());
        userInfo.setStatus(user.getStatus());
        userInfo.setGmtModified(new Date());
        userInfo.setGmtCreated(new Date());
        return userInfo;
    }
}
