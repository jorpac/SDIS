import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Operate extends Remote {
    String look_up(String data) throws RemoteException;
    String register(String data) throws RemoteException;
}