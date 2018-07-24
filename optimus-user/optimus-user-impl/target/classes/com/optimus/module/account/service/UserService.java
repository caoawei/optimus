package com.optimus.module.account.service;

import com.optimus.module.account.dal.entity.User;

import java.util.List;

/**
 * 用户服务
 */
public interface UserService {

    /**
     * 新增用户
     * @param user 用户
     * @return 大于0新增成功
     */
    int insertUser(User user);

    /**
     * 删除用户
     * @param id 用户id
     */
    void delete(Long id);

    /**
     * 修改用户信息
     * @param user 用户
     * @return
     */
    int updateUser(User user);

    /**
     * 查询用户
     * @param id 用户id
     * @return
     */
    User selectById(Long id);

    /**
     * 查询用户
     * @param mobile 手机号
     * @return
     */
    User selectByMobile(Long mobile);

    /**
     * 批量查询用户
     * @param ids 用户id
     * @return
     */
    List<User> listByIds(List<Long> ids);

    /**
     * 批量查询用户
     * @param mobile 手机号
     * @return
     */
    List<User> listByMobiles(List<Long> mobile);
}
