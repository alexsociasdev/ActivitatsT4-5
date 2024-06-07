import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VotingSystem extends Remote {
    void vote(String candidate) throws RemoteException;
    int getVotes(String candidate) throws RemoteException;
    String[] getCandidates() throws RemoteException;
}
