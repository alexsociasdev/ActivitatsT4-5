package SistemaMensajesDistribuidos;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServidorGestorMensajes extends UnicastRemoteObject implements GestorMensajesInterface {
    private Map<String, List<String>> mensajes;
    private List<String> usuarios;
    private Map<String, ClienteGestorMensajesInterface> clientes;

    protected ServidorGestorMensajes() throws RemoteException {
        mensajes = new HashMap<>();
        usuarios = new ArrayList<>();
        clientes = new HashMap<>();
    }

    @Override
    public void enviarMensaje(String remitente, String destinatario, String mensaje) throws RemoteException {
        if (mensajes.containsKey(destinatario)) {
            mensajes.get(destinatario).add("De " + remitente + ": " + mensaje);
            if (clientes.containsKey(destinatario)) {
                clientes.get(destinatario).notificarMensaje("De " + remitente + ": " + mensaje);
            }
        }
    }

    @Override
    public List<String> recibirMensajes(String usuario) throws RemoteException {
        return mensajes.getOrDefault(usuario, new ArrayList<>());
    }

    @Override
    public List<String> listarUsuarios() throws RemoteException {
        return new ArrayList<>(usuarios);
    }

    @Override
    public void registrarUsuario(String usuario, ClienteGestorMensajesInterface cliente) throws RemoteException {
        if (!usuarios.contains(usuario)) {
            usuarios.add(usuario);
            mensajes.put(usuario, new ArrayList<>());
            clientes.put(usuario, cliente);
        }
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(3000);
            registry.rebind("GestorMensajes", new ServidorGestorMensajes());
            System.out.println("Servidor de gestor de mensajes listo.");
        } catch (Exception ex) {
            System.err.println("Error " + ex.toString());
            ex.printStackTrace();
        }
    }
}
