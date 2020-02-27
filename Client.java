import java.io.IOException;
import java.net.*;

public class Client {
    public static void main(String args[]) throws IOException {
        if (args.length!=4) {
            System.out.println("Wrong number of args");
            return;
        }
        MulticastSocket dtSocket = new MulticastSocket();
        String oper = args[2];
        dtSocket.setTimeToLive(1);
        dtSocket.joinGroup(InetAddress.getByName(args[0]));
        dtSocket.connect(InetAddress.getByName(args[0]), Integer.parseInt(args[1]));
        byte[] buf2 = new byte[1024];
        DatagramPacket dtIPPacketRec = new DatagramPacket(buf2, 1024);

        System.out.println(args[0]);

        dtSocket.receive(dtIPPacketRec);
        System.out.println(dtIPPacketRec.getData().toString());

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
