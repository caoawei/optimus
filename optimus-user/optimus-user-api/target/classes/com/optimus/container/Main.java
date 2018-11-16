package com.optimus.container;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Main {

    public static void main(String[] args) {
//        StringBuilder rs = new StringBuilder("83");
//        int f = 8;
//        int t = 3;
//        for (int i = 3;i<=2018;i++) {
//            String v = String.valueOf(f+t);
//            String last = v.substring(v.length()-1);
//            rs.append(last);
//            f = t;
//            t = Integer.valueOf(last);
//        }
//
//        System.out.println(rs);
//        System.out.println(t);
//        System.out.println(rs.length());


        Random random = new Random();
        for (int i = 1;i<=7;i++) {
            if(i == 7) {
                // 随机输出 双色球蓝号
                System.out.println("|"+(random.nextInt(16)+1));
                continue;
            }

            System.out.print(random.nextInt(33)+1+" ");
        }

        for (int i = 1;i<=7;i++) {
            if(i == 7) {
                // 随机输出 双色球蓝号
                System.out.println("|"+(Double.valueOf(Math.random()*16).intValue()+1));
                continue;
            }

            System.out.print(Double.valueOf(Math.random()*33).intValue()+1+" ");
        }

        for (int i = 1;i<=7;i++) {
            if(i == 7) {
                // 随机输出 双色球蓝号
                System.out.println("|"+(ThreadLocalRandom.current().nextInt(16) +1));
                continue;
            }

            System.out.print(ThreadLocalRandom.current().nextInt(33)+1+" ");
        }

        int[] rs = new int[7];
        for (int i = 0;i<7;i++) {
            if(i == 6) {
                rs[i] = ThreadLocalRandom.current().nextInt(16) +1;
                continue;
            }

            rs[i] = ThreadLocalRandom.current().nextInt(33)+1;
        }

        // 升序 排序
        Arrays.sort(rs,0,rs.length-1);

        for (int i = 0;i<rs.length;i++) {
            if(i == 6) {
                System.out.println("|"+rs[i]);
            }
            System.out.print(rs[i]+" ");
        }
    }
}
