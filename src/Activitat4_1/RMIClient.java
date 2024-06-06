package Activitat4_1;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {
    public static void main(String[] args) {
        RMIInterface biblio = null;
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 4000);
            biblio = (RMIInterface) registry.lookup("Biblioteca");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        if (biblio != null){
            try {
                System.out.println("añadir libro " + biblio.ingresarLibro("selva"));
                System.out.println("consultar libro " + biblio.consultarLibro("selva"));
            System.out.println("eliminar libro " + biblio.eliminarLibro("selva"));
            }
            catch (Exception ex){
                System.err.println("Error en las consutlas " + ex.toString());
            }
        }
    }
}
