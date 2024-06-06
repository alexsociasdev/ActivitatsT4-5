package Activitat4_2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    public void recibirMensaje(String mensaje) throws RemoteException;
}
