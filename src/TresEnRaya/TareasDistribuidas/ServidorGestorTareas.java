package TresEnRaya.TareasDistribuidas;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServidorGestorTareas extends UnicastRemoteObject implements GestorTareasInterface {
    private List<String> tareas;
    private List<ClienteGestorTareasInterface> clientes;

    protected ServidorGestorTareas() throws RemoteException {
        tareas = new ArrayList<>();
        clientes = new ArrayList<>();
    }

    @Override
    public void agregarTarea(String tarea) throws RemoteException {
        tareas.add(tarea);
        notificarClientes("Tarea agregada: " + tarea);
    }

    @Override
    public void eliminarTarea(String tarea) throws RemoteException {
        tareas.remove(tarea);
        notificarClientes("Tarea eliminada: " + tarea);
    }

    @Override
    public List<String> listarTareas() throws RemoteException {
        return new ArrayList<>(tareas);
    }

    @Override
    public void registrarCliente(ClienteGestorTareasInterface cliente) throws RemoteException {
        clientes.add(cliente);
    }

    private void notificarClientes(String mensaje) throws RemoteException {
        for (ClienteGestorTareasInterface cliente : clientes) {
            cliente.notificarCambio(mensaje);
        }
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(3000);
            registry.rebind("GestorTareas", new ServidorGestorTareas());
            System.out.println("Servidor de gestor de tareas listo.");
        } catch (Exception ex) {
            System.err.println("Error " + ex.toString());
            ex.printStackTrace();
        }
    }
}
