package com.book;

/**
 * Created by Administrator on 2018/3/31.
 */
public class Member {
    // 会员号
    private String number;
    // 会员姓名
    private String name;
    // 会员等级
    private int level;
    // 折扣
    private double discount;
    // 剩余金额
    private double amount;
    // 密码
    private String password;
    // 是否登录
    private boolean isLogin;

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    /**
     * 折扣后的价格
     * @param price 原价
     * @return
     */
    public double afterDiscountPrice(double price){
        return price*discount;
    }
}
