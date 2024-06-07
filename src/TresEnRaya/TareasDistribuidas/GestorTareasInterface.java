package TresEnRaya.TareasDistribuidas;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface GestorTareasInterface extends Remote {
    void agregarTarea(String tarea) throws RemoteException;
    void eliminarTarea(String tarea) throws RemoteException;
    List<String> listarTareas() throws RemoteException;
    void registrarCliente(ClienteGestorTareasInterface cliente) throws RemoteException;
}
