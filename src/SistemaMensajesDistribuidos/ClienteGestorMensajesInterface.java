package SistemaMensajesDistribuidos;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClienteGestorMensajesInterface extends Remote {
    void notificarMensaje(String mensaje) throws RemoteException;
}
