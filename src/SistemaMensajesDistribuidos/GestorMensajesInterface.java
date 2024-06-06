package SistemaMensajesDistribuidos;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface GestorMensajesInterface extends Remote {
    void enviarMensaje(String remitente, String destinatario, String mensaje) throws RemoteException;
    List<String> recibirMensajes(String usuario) throws RemoteException;
    List<String> listarUsuarios() throws RemoteException;
    void registrarUsuario(String usuario, ClienteGestorMensajesInterface cliente) throws RemoteException;
}