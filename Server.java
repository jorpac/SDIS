
import example.hello.Hello;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class Server implements Operate {
    static final String SUCCESS = "SUCCESS";
    static final String ERROR = "ERROR";
    HashMap<String, String> dnsMap = new HashMap<String, String>();

    public static void main(String[] args) throws IOException {
        /*if(args.length!=1){
            System.out.println("USAGE ERROR: java Server <port number>");
            return;
        }*/

        try {
            Server obj = new Server();
            Operate stub = (Operate) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind(args[0], stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public String look_up(String data) throws RemoteException {
        String ip;
        System.out.println("LOOKUP");
        System.out.println("DNS= " + data);
        if (dnsMap.containsKey(data)) {
            return dnsMap.get(data);
        } else {
            return ERROR;
        }

    }


    @Override
    public String register(String data) throws RemoteException {
        String ip, dns;

        //data = dtRecPacket.getData().toString();
        System.out.println(data);
        System.out.println("RECEIVE");
        String ip2[] = data.split(" ");
        dns = ip2[0];
        ip = ip2[1];
        System.out.println("Ip= " + ip + "\nDns= " + dns);
        if (!dnsMap.containsKey(dns)) {
            dnsMap.put(dns, ip);
            return SUCCESS;
        } else
            return ERROR;
    }
}