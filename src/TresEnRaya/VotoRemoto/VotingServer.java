import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class VotingServer {
    public static void main(String[] args) {
        try {
            VotingSystem votingSystem = new VotingSystemImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("VotingSystem", votingSystem);
            System.out.println("Voting system server ready.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
