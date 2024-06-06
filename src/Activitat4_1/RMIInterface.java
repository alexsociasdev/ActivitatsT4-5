package Activitat4_1;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInterface extends Remote {

    public String consultarLibro (String libro) throws RemoteException;
    public String ingresarLibro (String libro) throws RemoteException;
    public String eliminarLibro (String libro) throws RemoteException;
}
