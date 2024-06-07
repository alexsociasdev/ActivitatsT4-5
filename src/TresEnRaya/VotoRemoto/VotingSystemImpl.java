import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class VotingSystemImpl extends UnicastRemoteObject implements VotingSystem {
    private Map<String, Integer> votes;

    public VotingSystemImpl() throws RemoteException {
        votes = new HashMap<>();
        votes.put("Candidate A", 0);
        votes.put("Candidate B", 0);
        votes.put("Candidate C", 0);
    }

    @Override
    public synchronized void vote(String candidate) throws RemoteException {
        if (votes.containsKey(candidate)) {
            votes.put(candidate, votes.get(candidate) + 1);
        }
    }

    @Override
    public synchronized int getVotes(String candidate) throws RemoteException {
        return votes.getOrDefault(candidate, 0);
    }

    @Override
    public synchronized String[] getCandidates() throws RemoteException {
        return votes.keySet().toArray(new String[0]);
    }
}
