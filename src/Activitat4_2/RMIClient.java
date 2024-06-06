package Activitat4_2;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class RMIClient extends UnicastRemoteObject implements ClientInterface {

    protected RMIClient() throws RemoteException {
        super();
    }

    @Override
    public void recibirMensaje(String mensaje) throws RemoteException {
        System.out.println(mensaje);
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 3000);

            ServerInterface serverInterface = (ServerInterface) registry.lookup("Chat");

            RMIClient cliente = new RMIClient();
            serverInterface.registrarCliente(cliente);

            Scanner scanner = new Scanner(System.in);
            while (true) {
                String mensaje = scanner.nextLine();
                serverInterface.escribirMensaje(mensaje);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
