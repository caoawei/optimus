package com.optimus.module.account.dal.entity;

import java.io.Serializable;
import java.util.Date;

public class UserInfo implements Serializable {
    private Long id;

    private Long userId;

    private Long mobile;

    // 昵称
    private String niceName;

    // 身份证
    private String idNumber;

    // 真实姓名
    private String realName;

    // 性别:(1|2 男|女)
    private Integer sex;

    // 生日;(yyyyMMdd)
    private String birthday;

    // 状态(0|1 注册未实名|实名认证)
    private Integer status;

    // 微信openId
    private String wechatId;

    // 第三方绑定类型(1|2 微信|QQ)
    private Integer bindType;

    // 绑卡标识:(0 未绑卡:1绑卡)
    private Integer bindBankFlag;

    private Date gmtCreated;

    private Date gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    /**
     * 返回昵称
     * @return 昵称
     */
    public String getNiceName() {
        return niceName;
    }

    /**
     * 设置昵称
     */
    public void setNiceName(String niceName) {
        this.niceName = niceName == null ? null : niceName.trim();
    }

    /**
     * 返回身份证
     * @return 身份证
     */
    public String getIdNumber() {
        return idNumber;
    }

    /**
     * 设置身份证
     */
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber == null ? null : idNumber.trim();
    }

    /**
     * 返回真实姓名
     * @return 真实姓名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 设置真实姓名
     */
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    /**
     * 返回性别:(1|2 男|女)
     * @return 性别:(1|2 男|女)
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 设置性别:(1|2 男|女)
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 返回生日;(yyyyMMdd)
     * @return 生日;(yyyyMMdd)
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * 设置生日;(yyyyMMdd)
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    /**
     * 返回状态(0|1 注册未实名|实名认证)
     * @return 状态(0|1 注册未实名|实名认证)
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态(0|1 注册未实名|实名认证)
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 返回微信openId
     * @return 微信openId
     */
    public String getWechatId() {
        return wechatId;
    }

    /**
     * 设置微信openId
     */
    public void setWechatId(String wechatId) {
        this.wechatId = wechatId == null ? null : wechatId.trim();
    }

    /**
     * 返回第三方绑定类型(1|2 微信|QQ)
     * @return 第三方绑定类型(1|2 微信|QQ)
     */
    public Integer getBindType() {
        return bindType;
    }

    /**
     * 设置第三方绑定类型(1|2 微信|QQ)
     */
    public void setBindType(Integer bindType) {
        this.bindType = bindType;
    }

    /**
     * 返回绑卡标识:(0 未绑卡:1绑卡)
     * @return 绑卡标识:(0 未绑卡:1绑卡)
     */
    public Integer getBindBankFlag() {
        return bindBankFlag;
    }

    /**
     * 设置绑卡标识:(0 未绑卡:1绑卡)
     */
    public void setBindBankFlag(Integer bindBankFlag) {
        this.bindBankFlag = bindBankFlag;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}