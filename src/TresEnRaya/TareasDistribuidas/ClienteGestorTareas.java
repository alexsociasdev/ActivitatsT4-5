package TresEnRaya.TareasDistribuidas;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Scanner;

public class ClienteGestorTareas extends UnicastRemoteObject implements ClienteGestorTareasInterface {

    protected ClienteGestorTareas() throws RemoteException {
        super();
    }

    @Override
    public void notificarCambio(String mensaje) throws RemoteException {
        System.out.println("Notificación del servidor: " + mensaje);
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 3000);
            GestorTareasInterface servidor = (GestorTareasInterface) registry.lookup("GestorTareas");

            ClienteGestorTareas cliente = new ClienteGestorTareas();
            servidor.registrarCliente(cliente);

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("1. Agregar tarea");
                System.out.println("2. Eliminar tarea");
                System.out.println("3. Listar tareas");
                System.out.print("Elige una opción: ");
                int opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        System.out.print("Ingresa la tarea: ");
                        String tarea = scanner.nextLine();
                        servidor.agregarTarea(tarea);
                        break;
                    case 2:
                        System.out.print("Ingresa la tarea a eliminar: ");
                        String tareaEliminar = scanner.nextLine();
                        servidor.eliminarTarea(tareaEliminar);
                        break;
                    case 3:
                        List<String> tareas = servidor.listarTareas();
                        System.out.println("Tareas pendientes:");
                        for (String t : tareas) {
                            System.out.println(t);
                        }
                        break;
                    default:
                        System.out.println("Opción no válida");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
