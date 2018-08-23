import org.junit.Test;
import org.junit.experimental.theories.Theories;

import java.lang.ref.WeakReference;

public class WeakTest {

    static int _1m = 1024*1024;

    @Test
    public void test(){


//        WeakReference<Object> wf1 = new WeakReference<>(new Object());

        Object obj = new Object();

        ThreadLocal<Object> local = new ThreadLocal<>();
        local.set(obj);

        int i=0;
//        int j=0;
        while (true) {

            if(local.get() == null) {
                break;
            } else {
                System.out.println("obj loop:"+(++i));
            }

//            if(wf1.get() == null) {
//                break;
//            } else {
//                System.out.println("wfl loop:"+(++j));
//
//            }
        }
    }

    public static void main(String[] args) {

        Runnable job = new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<10;i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"--> "+i);
                }
            }
        };
        Thread t1 = new Thread(job,"线程1");
        Thread t2 = new Thread(job,"线程2");

        t1.start();
        t2.start();
    }
}
