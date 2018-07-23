package com.book;

/**
 * Created by Administrator on 2018/3/31.
 */
public class BookSytem {

    private BookManager bookManager = new BookManager(20);

    private Member[] members;

    private int size;

    public BookSytem(int cap){
        cap = cap > 0 ? cap : 10;
        // 初始化 会员(暂无注册功能)
        members = new Member[cap];
        this.size = cap;
        int num = 2000;
        for (int i=0;i<members.length;i++){
            Member m = new Member();
            m.setNumber(String.valueOf(num++));
            m.setName("会员"+i);
            m.setDiscount(1-0.05*i);
            m.setLevel(i);
            m.setPassword(String.valueOf(123456));// 初始密码
            m.setLogin(false);
            members[i] = m;
        }
    }

    /**
     * 会员登录验证
     * @return
     */
    public boolean login(String number,String password){
        if(number==null || number.length() == 0){
            return false;
        }
        if(password==null || password.length() == 0){
            return false;
        }
        for (Member member : members){
            if(number.equals(member.getNumber()) && password.equals(member.getPassword())){
                member.setLogin(true);
                return true;
            }
        }
        return false;
    }

    public boolean addBook(Book book){
        bookManager.addBook(book);
        return true;
    }

    public boolean deleteBook(String number){
        bookManager.deleteBook(number);
        return true;
    }
    /**
     * 会员删除
     * @param number 会员号
     * @return 退换金额
     */
    public double deleteMember(String number){
        if(number==null||number.length()==0){
            return 0;
        }

        int index = -1;
        for (int i=0;i<members.length;i++){
            Member member = members[i];
            if(member.getNumber().equals(number) && member.isLogin()){
                index = i;
            }
        }

        if(index >= 0){
            Member m = members[index];
            if(index == members.length-1){
                members[index] = null;
            } else {
                for (;index<members.length-1;index++){
                    members[index] = members[index+1];
                }

                members[size-1] = null;
                this.size--;
            }

            return m==null?0:m.getAmount();
        }

        return 0;
    }

    /**
     * 计算会员图书折扣价
     * @param number 会员号
     * @param book
     * @return
     */
    public double discountPrice(String number,Book book) {
        Member m = null;
        for (int i=0;i<members.length;i++){
            Member member = members[i];
            if(member.getNumber().equals(number) && member.isLogin()){
                m = member;
            }
        }

        if(m == null){
            return book.getPrice();//未登录或非会员
        }
        return m.afterDiscountPrice(book.getPrice());
    }

    /**
     * 会员查询所有书
     * @return
     */
    public Book[] queryAll(String number) {
        Book[] books = this.bookManager.getBooks();
        // 不能直接修改book的价格，所以要拷贝
        Member m = getMember(number);
        if(m == null){
            return books;
        }

        // 计算图书的折扣价,以折扣价显示给用户
        Book[] rs = new Book[bookManager.getSize()];
        for (int i=0;i<bookManager.getSize();i++){
            rs[i] = books[i];
            rs[i].setPrice(m.afterDiscountPrice(rs[i].getPrice()));
        }
        return rs;
    }

    private Member getMember(String number){
        for (int i=0;i<members.length;i++){
            Member member = members[i];
            if(member.getNumber().equals(number) && member.isLogin()){
                return member;
            }
        }

        return null;
    }

    private Book copy(Book book){
        Book n = new Book();
        n.setPublisher(book.getPublisher());
        n.setReserve(book.getReserve());
        n.setPrice(book.getPrice());
        n.setBookName(book.getBookName());
        n.setBookNum(book.getBookNum());
        return n;
    }
}
