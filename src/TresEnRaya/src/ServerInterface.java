package TresEnRaya.src;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface ServerInterface extends Remote{

    public void addUser(JugadorInterface jugador) throws RemoteException;
    public void setFichasToPlayers() throws RemoteException;
    public void getPositionToSetFicha(int fila, int columna, UUID jugadorUUID) throws RemoteException;
    public Tablero.FICHA getAssignedFicha(JugadorInterface jugador) throws RemoteException;
    public void checkWin(UUID jugadorUUID) throws RemoteException;
    public void seeTable() throws RemoteException;

    public void salir(UUID jugadorUUID) throws RemoteException;

    public int getNumUsers() throws RemoteException;
}
