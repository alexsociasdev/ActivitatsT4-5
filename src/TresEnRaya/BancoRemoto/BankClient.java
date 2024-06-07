import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class BankClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            BankSystem bankSystem = (BankSystem) registry.lookup("BankSystem");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter account name: ");
            String accountName = scanner.nextLine();
            bankSystem.createAccount(accountName);
            System.out.println("Account created!");

            while (true) {
                System.out.println("1. Deposit");
                System.out.println("2. Withdraw");
                System.out.println("3. Check Balance");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                if (choice == 4) {
                    break;
                }
                switch (choice) {
                    case 1:
                        System.out.print("Enter amount to deposit: ");
                        double depositAmount = scanner.nextDouble();
                        bankSystem.deposit(accountName, depositAmount);
                        System.out.println("Deposit successful!");
                        break;
                    case 2:
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        bankSystem.withdraw(accountName, withdrawAmount);
                        System.out.println("Withdrawal successful!");
                        break;
                    case 3:
                        double balance = bankSystem.getBalance(accountName);
                        System.out.println("Current balance: " + balance);
                        break;
                }
            }

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
