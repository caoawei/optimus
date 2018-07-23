package com.book;

/**
 * Created by Administrator on 2018/3/31.
 */
public class Book {

    // 书号
    private String bookNum;
    // 书名
    private String bookName;
    // 出版社
    private String publisher;
    // 单价
    private double price;
    // 库存
    private int reserve;

    public String getBookNum() {
        return bookNum;
    }

    public void setBookNum(String bookNum) {
        this.bookNum = bookNum;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getReserve() {
        return reserve;
    }

    public void setReserve(int reserve) {
        this.reserve = reserve;
    }

    @Override
    public String toString() {
        return "{" +
                "书号='" + bookNum + '\'' +
                ", 书名='" + bookName + '\'' +
                ", 出版社='" + publisher + '\'' +
                ", 价格=" + price +
                ", 库存=" + reserve +
                '}';
    }
}
