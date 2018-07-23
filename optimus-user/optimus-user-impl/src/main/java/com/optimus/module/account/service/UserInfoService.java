package com.optimus.module.account.service;

import com.optimus.module.account.dal.entity.User;
import com.optimus.module.account.dal.entity.UserInfo;

/**
 * 用户service
 */
public interface UserInfoService {

    /**
     * 新增用户信息
     * @param user 用户信息
     * @return
     */
    int save(User user);

    /**
     * 删除用户信息
     * @param userId 用户id
     */
    void deleteByUserId(Long userId);

    /**
     * 删除用户信息
     * @param id userInfo id
     */
    void deleteById(Long id);

    /**
     * 删除用户信息
     * @param mobile 手机号
     */
    void deleteByMobile(Long mobile);

    /**
     * 删除用户信息
     * @param idNumber 身份证号
     */
    void deleteByIdNumber(String idNumber);

    /**
     * 修改用户信息
     * @param userInfo 用户信息
     * @return
     */
    int update(UserInfo userInfo);

    /**
     * 查询用户信息
     * @param id id
     * @return
     */
    UserInfo selectById(Long id);

    /**
     * 查询用户信息
     * @param userId 用户id
     * @return
     */
    UserInfo selectByUserId(Long userId);

    /**
     * 查询用户id
     * @param mobile 手机号
     * @return
     */
    UserInfo selectByMobile(Long mobile);

    /**
     * 查询用户信息
     * @param idNumber 身份证号
     * @return
     */
    UserInfo selectByIdNumber(String idNumber);

    /**
     * 微信公众号绑定
     * @param openId 用户微信身份标识
     */
    void bindWechat(String openId,Long userId);

    /**
     * 微信公众号解绑
     * @param openId 用户微信身份标识
     */
    void unbindWechat(String openId);
}
