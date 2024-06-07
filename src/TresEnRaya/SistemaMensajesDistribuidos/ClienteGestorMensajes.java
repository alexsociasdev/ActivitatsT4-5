package TresEnRaya.SistemaMensajesDistribuidos;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Scanner;

public class ClienteGestorMensajes extends UnicastRemoteObject implements ClienteGestorMensajesInterface {

    private String usuario;

    protected ClienteGestorMensajes(String usuario) throws RemoteException {
        super();
        this.usuario = usuario;
    }

    @Override
    public void notificarMensaje(String mensaje) throws RemoteException {
        System.out.println("Mensaje nuevo: " + mensaje);
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 3000);
            GestorMensajesInterface servidor = (GestorMensajesInterface) registry.lookup("GestorMensajes");

            Scanner scanner = new Scanner(System.in);

            System.out.print("Ingresa tu nombre de usuario: ");
            String usuario = scanner.nextLine();

            ClienteGestorMensajes cliente = new ClienteGestorMensajes(usuario);
            servidor.registrarUsuario(usuario, cliente);

            while (true) {
                System.out.println("1. Enviar mensaje");
                System.out.println("2. Recibir mensajes");
                System.out.println("3. Listar usuarios");
                System.out.print("Elige una opción: ");
                int opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        System.out.print("Ingresa el destinatario: ");
                        String destinatario = scanner.nextLine();
                        System.out.print("Ingresa el mensaje: ");
                        String mensaje = scanner.nextLine();
                        servidor.enviarMensaje(usuario, destinatario, mensaje);
                        break;
                    case 2:
                        List<String> mensajes = servidor.recibirMensajes(usuario);
                        System.out.println("Mensajes recibidos:");
                        for (String m : mensajes) {
                            System.out.println(m);
                        }
                        break;
                    case 3:
                        List<String> usuarios = servidor.listarUsuarios();
                        System.out.println("Usuarios conectados:");
                        for (String u : usuarios) {
                            System.out.println(u);
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
