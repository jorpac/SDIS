import com.sun.org.apache.bcel.internal.generic.RETURN;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;

public class Server{
    public static void main(String[] args) throws IOException {
        if(args.length!=1){
            System.out.println("USAGE ERROR: java Server <port number>");
            return;
        }
        HashMap<String, String> dnsMap = new HashMap<String, String>();
        DatagramSocket dtSocket = new DatagramSocket(8888);
        byte[] buf = new byte[1024];
        DatagramPacket dtRecPacket = new DatagramPacket(buf, 1024);
        String oper;
        String data;
        String ip, dns;

        while (true){
            dtSocket.receive(dtRecPacket);
            data = dtRecPacket.getData().toString();
            oper = data.substring(0, data.indexOf(' '));
            if(oper.equals("register")){
                System.out.println("RECEIVE");

            }
            else if (oper.equals("lookup")){
                System.out.println("LOOKUP");
            }
        }
    }
}

