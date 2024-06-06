package Activitat4_2;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RMIServer extends UnicastRemoteObject implements ServerInterface {
    private List<ClientInterface> clientes;

    protected RMIServer() throws RemoteException {
        clientes = new ArrayList<>();
    }

    @Override
    public void escribirMensaje(String mensaje) throws RemoteException {
        enviarMensaje(mensaje);
    }

    @Override
    public void registrarCliente(ClientInterface cliente) throws RemoteException {
        clientes.add(cliente);
    }

    @Override
    public void enviarMensaje(String mensaje) throws RemoteException {
        for (ClientInterface cliente : clientes) {
            cliente.recibirMensaje(mensaje);
        }
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(3000);
            registry.rebind("Chat", new RMIServer());
            System.out.println("Servidor listo.");
        } catch (Exception ex) {
            System.err.println("Error " + ex.toString());
            ex.printStackTrace();
        }
    }
}
