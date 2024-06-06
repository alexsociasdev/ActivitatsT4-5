package TareasDistribuidas;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClienteGestorTareasInterface extends Remote {
    void notificarCambio(String mensaje) throws RemoteException;
}
