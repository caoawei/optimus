import org.apache.commons.lang.text.StrBuilder;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Administrator on 2018/5/16.
 */
public class SocketTest {


    public static void main(String[] args) {
        try {
            Socket socket = new Socket();
            socket.setKeepAlive(true);
            socket.connect(new InetSocketAddress("127.0.0.1",2181));
            socket.setTcpNoDelay(true);
            OutputStream os = socket.getOutputStream();
            os.write("Hello World".getBytes());
            os.flush();

            InputStream is = socket.getInputStream();
            BufferedReader bis = new BufferedReader(new InputStreamReader(is));
            String line = bis.readLine();
            StringBuilder sb = new StringBuilder();
            while ((line) != null) {
                sb.append(line);
            }

            is.close();
            System.out.println(sb);
            socket.close();
            socket.shutdownInput();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
