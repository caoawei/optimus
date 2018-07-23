package test;

import com.alibaba.com.caucho.hessian.io.Hessian2Output;
import com.alibaba.dubbo.common.serialize.support.hessian.Hessian2ObjectInput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Administrator on 2018/5/10.
 */
public class Main {

    public static void main(String[] args) {
        try {

//            System.out.println(SubTest.AD);
//            System.out.println(SubTest.NAME);
//            System.out.println(SubTest.getNAME());
            ThreadLocal<String> tl = new ThreadLocal<>();
            SubTest.static_syn();
            SubTest st = new SubTest();
            st.syn();

            tl.set("test1");
            tl.set("test2");
            tl.set("test3");
            tl.set("test4");
            System.out.println(tl.get());
            System.out.println(tl.get());
            System.out.println(tl.get());
            System.out.println(tl.get());

            ObjectOutputStream os = new ObjectOutputStream(new DataOutputStream(new ByteArrayOutputStream()));
            Hessian2Output hessian2Output = new Hessian2Output(os);
            hessian2Output.writeObject(st);
            System.out.println();


            Thread.dumpStack();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
