package com.optimus.module.order.dal.entity;

import java.io.Serializable;
import java.util.Date;
import javax.xml.crypto.Data;

public class Order implements Serializable {
    /**
     * 
     *
     */
    private Long id;

    /**
     * 用户ID
     *
     */
    private Long userId;

    /**
     * 项目ID
     *
     */
    private Long projectId;

    /**
     * 优惠券ID
     *
     */
    private Long couponId;

    /**
     * 订单金额
     *
     */
    private Long orderAmount;

    /**
     * 使用优惠券金额
     *
     */
    private Long couponAmount;

    /**
     * 奖励金额
     *
     */
    private Long rewardAmount;

    /**
     * 用户支付金额
     *
     */
    private Long payAmount;

    /**
     * 项目名称
     *
     */
    private String projectName;

    /**
     * 订单状态: -1 支付失败; 0 待支付; 1 支付中; 2 支付完成
     *
     */
    private Integer status;

    /**
     * 父订单
     *
     */
    private Long orderParentId;

    /**
     * 创建时间
     *
     */
    private Date gmtCreated;

    /**
     * 修改时间
     *
     */
    private Date gmtModify;

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

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Long getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Long couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Long getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(Long rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    public Long getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Long payAmount) {
        this.payAmount = payAmount;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getOrderParentId() {
        return orderParentId;
    }

    public void setOrderParentId(Long orderParentId) {
        this.orderParentId = orderParentId;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

}