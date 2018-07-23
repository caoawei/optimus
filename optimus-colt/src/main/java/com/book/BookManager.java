package com.book;

import java.util.Arrays;

/**
 * Created by Administrator on 2018/3/31.
 */
public class BookManager {

    private Book[] books;
    // 当前位置
    private int index;
    // 大小
    private int size;

    public BookManager(int initCap){
        // 系统初始容量,默认20
        int cap = Math.max(20,initCap);
        this.books = new Book[cap];
        this.index = 0;
        this.size = 0;
    }

    /**
     * 添加图书
     * @param book
     */
    public void addBook(Book book){
        if(book == null){
            return;
        }

        if(isFull()){
            // 扩充容量
            expandCap();
        }

        boolean flag = false;
        for (int i=0;i<this.size;i++){
            Book bk = books[i];
            // 如果存在书号相同的,则将库存+1,否则则执行后面的插入新图书
            if(bk.getBookNum().equals(book.getBookNum())){
                bk.setReserve(bk.getReserve()+1);
                flag = true;
            }
        }

        if(!flag){
            book.setReserve(1);
            books[index++] = book;
            book.setReserve(1);
            this.size++;
        }
    }

    /**
     * 根据书号来修改
     * @param book
     */
    public void modifyBook(Book book){
        if(book == null){
            return;
        }
        for (int i=0;i<this.size;i++){
            Book bk = books[i];
            if(bk.getBookNum().equals(book.getBookNum())){
                books[i] = book;
            }
        }
    }

    /**
     * 查询所有图书
     * @return
     */
    public Book[] queryAllBool(){
        if(this.size == 0){
            return null;
        }
        return this.books;
    }

    public void deleteBook(String bookNum){
        if(this.size == 0){
            return;
        }

        // 找出与书号相同的书所在位置
        int index = -1;
        for (int i=0;i<this.size;i++){
            Book bk = books[i];
            if(bk.getBookNum().equals(bookNum)){
                index = i;
            }
        }

        if(index >= 0){
            Book book = this.books[index];

            // 库存大于 1,直接减库存
            if(book.getReserve() > 1){
                book.setReserve(book.getReserve()-1);
            }
            // 库存为<=1,直接从数组里移除,然后将后续的书往前移动.
            else {
                for (;index < this.size-1;index++){
                    books[index] = books[index+1];
                }

                books[this.size-1] = null;
                this.size--;
                this.index--;
            }
        }

    }

    /**
     * 扩充数组
     */
    private void expandCap() {
        Book[] newBooks = new Book[this.books.length*2];
        System.arraycopy(books,0,newBooks,0,books.length);
        this.books = newBooks;
    }

    /**
     * 数组是否满
     * @return
     */
    private boolean isFull(){
        return size == books.length;
    }

    public Book[] getBooks() {
        return books;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setBooks(Book[] books) {
        this.books = books;
    }

    public static void main(String[] args){

        // 1. 测试添加
        System.out.println("=======================================测试添加===============================================");
        int num = 10000;
        BookManager bookManager = new BookManager(20);
        for (int i=0;i<25;i++){
            Book b = new Book();
            b.setBookNum(String.valueOf(num++));
            b.setBookName("java基础");
            b.setPrice(25.5);
            b.setReserve(1);
            b.setPublisher("高等教育出版社");
            bookManager.addBook(b);
        }

        int num2 = 10000;
        for (int i=0;i<25;i++){
            Book b = new Book();
            b.setBookNum(String.valueOf(num2++));
            b.setBookName("java基础");
            b.setPrice(25.5);
            b.setReserve(1);
            b.setPublisher("高等教育出版社");
            bookManager.addBook(b);
        }

        print(bookManager);

        // 2.测试删除
        System.out.println("====================================测试删除============================================");
        bookManager.deleteBook("10001");
        print(bookManager);

        // 3.测试查询
        System.out.println("====================================测试查询============================================");
        Book[] rs = bookManager.queryAllBool();
        print(bookManager);
    }

    private static void print(BookManager bookManager){
        for (int i=0;i<bookManager.books.length;i++){
            if(bookManager.books[i] == null){
                break;
            }
            System.out.println(bookManager.books[i]);
        }
    }
}
