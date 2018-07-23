package test;

/**
 * Created by Administrator on 2018/5/17.
 */
public class SubTest extends Test {

    static {
        System.out.println("子类开始加载....");
    }

    public SubTest() {
        System.out.println("子类实例开始初始化...");
    }

}
