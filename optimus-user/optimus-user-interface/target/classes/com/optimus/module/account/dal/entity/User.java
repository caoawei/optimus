package com.optimus.module.account.dal.entity;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private Long id;

    private Long mobile;

    private String password;

    // 随机密码掩码
    private String mask;

    // 0,1,2(注册未实名,实名认证)
    private Integer status;

    // 微信公众号
    private String wechatId;

    // 第三方绑定id
    private String unionId;

    // 绑定类型(0|1|2 未绑定|微信绑定|QQ绑定)
    private Integer bindType;

    private Date gmtCreated;

    private Date gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 返回随机密码掩码
     * @return 随机密码掩码
     */
    public String getMask() {
        return mask;
    }

    /**
     * 设置随机密码掩码
     */
    public void setMask(String mask) {
        this.mask = mask == null ? null : mask.trim();
    }

    /**
     * 返回0,1,2(注册未实名,实名认证)
     * @return 0,1,2(注册未实名,实名认证)
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0,1,2(注册未实名,实名认证)
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 返回微信公众号
     * @return 微信公众号
     */
    public String getWechatId() {
        return wechatId;
    }

    /**
     * 设置微信公众号
     */
    public void setWechatId(String wechatId) {
        this.wechatId = wechatId == null ? null : wechatId.trim();
    }

    /**
     * 返回第三方绑定id
     * @return 第三方绑定id
     */
    public String getUnionId() {
        return unionId;
    }

    /**
     * 设置第三方绑定id
     */
    public void setUnionId(String unionId) {
        this.unionId = unionId == null ? null : unionId.trim();
    }

    /**
     * 返回绑定类型(0|1|2 未绑定|微信绑定|QQ绑定)
     * @return 绑定类型(0|1|2 未绑定|微信绑定|QQ绑定)
     */
    public Integer getBindType() {
        return bindType;
    }

    /**
     * 设置绑定类型(0|1|2 未绑定|微信绑定|QQ绑定)
     */
    public void setBindType(Integer bindType) {
        this.bindType = bindType;
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