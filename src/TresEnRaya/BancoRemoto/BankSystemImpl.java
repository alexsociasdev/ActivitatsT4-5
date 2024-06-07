import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class BankSystemImpl extends UnicastRemoteObject implements BankSystem {
    private Map<String, Double> accounts;

    public BankSystemImpl() throws RemoteException {
        accounts = new HashMap<>();
    }

    @Override
    public synchronized void createAccount(String accountName) throws RemoteException {
        accounts.putIfAbsent(accountName, 0.0);
    }

    @Override
    public synchronized void deposit(String accountName, double amount) throws RemoteException {
        accounts.put(accountName, accounts.getOrDefault(accountName, 0.0) + amount);
    }

    @Override
    public synchronized void withdraw(String accountName, double amount) throws RemoteException {
        double balance = accounts.getOrDefault(accountName, 0.0);
        if (balance >= amount) {
            accounts.put(accountName, balance - amount);
        } else {
            throw new RemoteException("Insufficient funds");
        }
    }

    @Override
    public synchronized double getBalance(String accountName) throws RemoteException {
        return accounts.getOrDefault(accountName, 0.0);
    }
}
