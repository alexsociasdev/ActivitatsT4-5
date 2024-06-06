package TresEnRaya.src;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface JugadorInterface extends Remote{
    public UUID getUUID();
}
