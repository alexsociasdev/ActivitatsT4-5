import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class BankServer {
    public static void main(String[] args) {
        try {
            BankSystem bankSystem = new BankSystemImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("BankSystem", bankSystem);
            System.out.println("Bank system server ready.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
