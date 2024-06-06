package Activitat4_1;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RMIServer implements  RMIInterface{
    public ArrayList<String> libros = new ArrayList<>();

    public String consultarLibro(String libro){

        for (String book: libros) {
            if(book == libro)
                return "El libro " + book + " existe en la biblioteca virtual";
            else
                return "Libro no encontrado";
        }
        return libro;
    }

    public String ingresarLibro(String libro){
        if (!libros.contains(libro)){
            libros.add(libro);
            return "El libro " + libro + " ha sido añadido";
        }
        else
            return "El libro ya existe en la biblioteca";
    }

    public String eliminarLibro(String libro){
        if (libros.contains(libro)){
            libros.remove(libro);
            return "El libro " + libro + " ha sido eliminado";
        }
        else return "El libro no existe en la biblioteca";
    }

    public static void main(String[] args) {
        Registry registry = null;
        try {
            registry = LocateRegistry.createRegistry(4000);
        }catch (Exception ex){
            System.err.println("Error, no se ha podido conectar al registro");
        }
        RMIServer serverObject = new RMIServer();
        try {
            registry.bind("Biblioteca", (RMIInterface) UnicastRemoteObject.exportObject(serverObject, 0));
        }
        catch (Exception ex){
            System.err.println("Error al ingresar al objeto servidor " + ex.toString());
        }
    }
}
