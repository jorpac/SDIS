import com.sun.org.apache.bcel.internal.generic.RETURN;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;

public class Server{
    static final String SUCCESS = "SUCCESS";
    static final String ERROR = "ERROR";
    public static void main(String[] args) throws IOException {
        /*if(args.length!=1){
            System.out.println("USAGE ERROR: java Server <port number>");
            return;
        }*/
        HashMap<String, String> dnsMap = new HashMap<String, String>();
        DatagramSocket dtSocket = new DatagramSocket(8888);
        byte[] buf = new byte[1024];
        

        while (true){
            DatagramPacket dtRecPacket = new DatagramPacket(buf, 1024);

        
            byte[] ret_buf = new byte[1024];
            DatagramPacket dtRetPacket;
            
            String oper;
            String data;
            String ip, dns;
            dtSocket.receive(dtRecPacket);
            data = new String(dtRecPacket.getData());
            //data = dtRecPacket.getData().toString();
            System.out.println(data);
            Integer first_space = data.indexOf(' ');
            oper = data.substring(0, first_space);
            if(oper.equals("register")){
                System.out.println("RECEIVE");
                String ip1 = data.substring(first_space);
                String ip2[] = ip1.split(" ");
                dns = ip2[1];
                ip = ip2[2];
                System.out.println("Ip= "+ ip +"\nDns= "+ dns);
                if(!dnsMap.containsKey(dns)){
                    dnsMap.put(dns, ip);
                    dtRetPacket = new DatagramPacket((SUCCESS).getBytes(), SUCCESS.length(), dtRecPacket.getAddress(), 8888);
                    dtSocket.send(dtRetPacket);
                }
                else{
                    dtRetPacket = new DatagramPacket((ERROR).getBytes(), ERROR.length(), dtRecPacket.getAddress(), 8888);
                    dtSocket.send(dtRetPacket);
                }
            }
            else if (oper.equals("lookup")){
                System.out.println("LOOKUP");
                dns = data.substring(first_space);
                System.out.println("DNS= " + dns);
                if(dnsMap.containsKey(dns)){
                    ip = dnsMap.get(dns);
                    dtRetPacket = new DatagramPacket((SUCCESS + dns + ip).getBytes(), SUCCESS.length() + ip.length() + dns.length() +1, dtRecPacket.getAddress(), 8888);
                    dtSocket.send(dtRetPacket);
                }
                else{
                    dtRetPacket = new DatagramPacket((ERROR).getBytes(), ERROR.length(), dtRecPacket.getAddress(), 8888);
                    dtSocket.send(dtRetPacket);
                }
            }
        }
    }
}

