package test;


import com.optimus.common.exception.BizException;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Administrator on 2018/5/17.
 */
public class Test implements Serializable  {

    public static String NAME = "name";
    public static final String AD = "ad";
    private static int age;

    private final String AD_INSTANCE = "ad";
    private String NAME_INSTANCE = "name";
    private static volatile int flag;

    static {
        System.out.println("父类开始加载....");
        age = 12;
        flag = 100;
    }

    public Test() {
        System.out.println("父类实例开始初始化....");
    }
    public static String getNAME() {
        return NAME;
    }

    public static void setNAME(String NAME) {
        Test.NAME = NAME;
    }

    public static String getAD() {
        return AD;
    }

    public static int getAge() {
        return age;
    }

    public static void setAge(int age) {
        Test.age = age;
    }

    public String getAD_INSTANCE() {
        return AD_INSTANCE;
    }

    public String getNAME_INSTANCE() {
        return NAME_INSTANCE;
    }

    public void setNAME_INSTANCE(String NAME_INSTANCE) {
        this.NAME_INSTANCE = NAME_INSTANCE;
    }

    public static synchronized void static_syn(){
        System.out.println("static_syn() 方法调用, 是否持有Test.class锁? = "+Thread.holdsLock(Test.class));
    }

    public synchronized void syn(){
        System.out.println("syn() 方法调用, 是否持有Test.class锁? = "+Thread.holdsLock(Test.class));
        System.out.println("syn() 方法调用, 是否持有this锁? = "+Thread.holdsLock(this));
        synchronized (this) {
            //throw new BizException("");
        }
        ThreadLocalRandom.current().longs();
        StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        for (StackTraceElement s : ste) {
            System.out.println(s.getClassName()+"------"+s.getMethodName());
        }
    }
}
