import example.hello.Hello;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String args[]) throws IOException {
        String response = "";
        if (args.length!=4) {
            System.out.println("Wrong number of args");
            return;
        }
        try {

            Registry registry = LocateRegistry.getRegistry(args[0]);
            Operate stub = (Operate) registry.lookup(args[1]);
            if(args[2].equals("register")) {
                response = stub.register(args[3]);
            }
            else if(args[2].equals("lookup")) {
                response = stub.look_up(args[3]);
            }
            System.out.println("response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

}
