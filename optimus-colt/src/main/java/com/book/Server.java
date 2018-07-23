package com.book;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

public class Server {

    private BookSytem bookSytem;

    private String number;

    public static void main(String[] args){
        Server server = new Server();
        server.bookSytem = new BookSytem(1);
        System.out.println("图书管理系统启动");

        while (true){
            System.out.println("以下是您可选择的操作项:-----------------------------");
            System.out.println("输入1------->添加书籍");
            System.out.println("输入2------->查询所有书籍");
            System.out.println("输入3------->修改数据书籍");
            System.out.println("输入4------->删除书籍");
            Scanner scanner = new Scanner(System.in);
            int data = scanner.nextInt();
            switch (data){
                case 1 :
                    server.addBook();
                    break;
                case 2 :
                    server.queryAllBook();
                    break;
                case 3 :
                    server.addBook();
                    break;
                case 4 :
                    server.delete();
                    break;
            }
        }


    }

    private void addBook(){
        String dir = "n";
        do{
            System.out.println("请按照指定顺序输入书籍信息,以'英文逗号 , '作为分割(书号,书名,出版社,价格):");
            Scanner scanner = new Scanner(System.in);
            String data = scanner.nextLine();
            if(data == null || data.length()==0 || data.matches("\\s*")){
                System.out.println("输入不合法,将回到主菜单");
                break;
            }
            String[] record = data.split(",");
            if(record.length != 4){
                System.out.println("输入不合法,将回到主菜单");
                break;
            }
            Book book = new Book();
            book.setBookNum(record[0]);
            book.setBookName(record[1]);
            book.setPublisher(record[2]);
            book.setPrice(Double.valueOf(record[3]));
            bookSytem.addBook(book);
            System.out.println("添加书籍成功,是否继续添加?y/n");
            Scanner scanner2 = new Scanner(System.in);
            dir = scanner.nextLine();
        }while ("y".equals(dir));

    }

    private void queryAllBook(){
        Book[] books = bookSytem.queryAll(number);
        if(books == null || books.length == 0){
            System.out.println("暂无书籍!");
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i=0;i<books.length;i++){
                if(books[i] == null){
                    break;
                }
                sb.append(books[i].toString()).append("\n");
            }
            System.out.println(sb.toString());
        }
    }


    private void delete(){
        System.out.print("请按输入书号:");
        Scanner scanner = new Scanner(System.in);
        String data = scanner.next();
        if(data == null || data.length() == 0){
            System.out.println("输入数据无效,将退回主菜单!");
        } else {
            bookSytem.deleteBook(data);
            System.out.println("删除成功!");
        }
    }
}
