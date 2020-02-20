import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Client {
    public static void main(String args[]) throws IOException {
        if (args.length!=4) {
            System.out.println("Wrong number of args");
            return;
        }
        DatagramSocket dtSocket = new DatagramSocket(Integer.parseInt(args[1]));
        String oper = args[2];
        DatagramPacket dtPacket = new DatagramPacket((args[2] + " " + args[3]).getBytes(), args[2].length() + args[3].length(), InetAddress.getByName(args[0]), Integer.parseInt(args[1]));
        byte[] buf = new byte[1024];
        DatagramPacket dtPacketRec = new DatagramPacket(buf, 1024);
        if(oper.equals("register") || oper.equals("lookup")){
            if(dtSocket.isConnected()){
                dtSocket.send(dtPacket);
            }
            else{
                dtSocket.connect(InetAddress.getByName(args[0]), Integer.parseInt(args[1]));
                dtSocket.send(dtPacket);
            }

            dtSocket.receive(dtPacketRec);
        }
        else{
            System.out.println(oper + "Wrong operation request");
            return;
        }
        System.out.println(args[3]);
        return;
    }

}
