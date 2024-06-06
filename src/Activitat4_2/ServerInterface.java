package Activitat4_2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
    void escribirMensaje(String mensaje) throws RemoteException;
    void registrarCliente(ClientInterface cliente) throws RemoteException;
    void enviarMensaje(String mensaje) throws RemoteException;
}
