package com.book;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Administrator on 2018/3/31.
 */
public class Main {

    public static void main(String[] args){
        JFrame win = new JFrame("图书管理系统");
        win.setSize(800, 500); // 设置窗体大小
        win.setLocation(400,200);
        win.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        win.setJMenuBar(getMenuBar());

        Container container = win.getContentPane(); // 获取一个容器
        JLabel jl=new JLabel("这是一个Jframe窗体");
        jl.setHorizontalAlignment(SwingConstants.CENTER);
        jl.setVerticalAlignment(SwingConstants.CENTER);

        container.add(jl);
        container.setBackground(Color.white);//设置容器的背景颜色

        win.setVisible(true); // 使窗体可视
    }

    private static JMenuBar getMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menuAdd = new JMenu("添加"), menuQuery = new JMenu("查询"), menuDelete = new JMenu("删除");
        menuBar.add(menuAdd);
        menuBar.add(menuQuery);
        menuBar.add(menuDelete);
        return menuBar;
    }
}
