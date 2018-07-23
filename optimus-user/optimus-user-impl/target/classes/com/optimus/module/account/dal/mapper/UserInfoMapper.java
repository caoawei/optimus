package com.optimus.module.account.dal.mapper;

import com.optimus.module.account.dal.entity.UserInfo;
import com.optimus.module.account.dal.entity.UserInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInfoMapper {
    /**
     * 获取符合条件的记录数
     * @param example 查询条件对象
     * @return 记录数
     */
    int countByExample(UserInfoExample example);

    /**
     * 批量删除符合条件的记录
     * @param example 查询条件对象
     * @return 成功删除的数量
     */
    int deleteByExample(UserInfoExample example);

    /**
     * 根据主键删除
     * @param id 主键值
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 新增记录
     * @param record 待新增的对象
     */
    int insert(UserInfo record);

    /**
     * 新增记录 <font color='blue'>（该操作为选择性的，只对record中不为空的属性生成sql语句）</font>
     * @param record 待新增的对象
     */
    int insertSelective(UserInfo record);

    /**
     * 批量查询
     * @param example 查询条件对象
     */
    List<UserInfo> selectByExample(UserInfoExample example);

    /**
     * 根据主键查询
     * @param id 主键值
     */
    UserInfo selectByPrimaryKey(Long id);

    /**
     * 指量更新,将符合条件的记录统一更新为record的值 <font color='blue'>（该操作为选择性的，只对record中不为空的属性生成sql语句）</font>
     * @param record 待更新到数据库的值
     * @param example 查询条件对象
     * @return 成功操作的记录数
     */
    int updateByExampleSelective(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    /**
     * 指量更新,将符合条件的记录统一更新为record的值 
     * @param record 待更新到数据库的值
     * @param example 查询条件对象
     * @return 成功操作的记录数
     */
    int updateByExample(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    /**
     * 根据主键更新记录 <font color='blue'>（该操作为选择性的，只对record中不为空的属性生成sql语句）</font>
     * @param record 记录
     */
    int updateByPrimaryKeySelective(UserInfo record);

    /**
     * 根据主键更新记录
     * @param record 记录
     */
    int updateByPrimaryKey(UserInfo record);
}