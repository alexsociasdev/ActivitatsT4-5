import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class VotingClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            VotingSystem votingSystem = (VotingSystem) registry.lookup("VotingSystem");

            Scanner scanner = new Scanner(System.in);
            System.out.println("Candidates: ");
            for (String candidate : votingSystem.getCandidates()) {
                System.out.println(candidate);
            }

            System.out.print("Enter your vote: ");
            String vote = scanner.nextLine();
            votingSystem.vote(vote);
            System.out.println("Thank you for voting!");

            for (String candidate : votingSystem.getCandidates()) {
                System.out.println(candidate + ": " + votingSystem.getVotes(candidate));
            }

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
