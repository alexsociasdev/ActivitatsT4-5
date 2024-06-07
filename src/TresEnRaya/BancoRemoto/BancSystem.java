import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BankSystem extends Remote {
    void createAccount(String accountName) throws RemoteException;
    void deposit(String accountName, double amount) throws RemoteException;
    void withdraw(String accountName, double amount) throws RemoteException;
    double getBalance(String accountName) throws RemoteException;
}
