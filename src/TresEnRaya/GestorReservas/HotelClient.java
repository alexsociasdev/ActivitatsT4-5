import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class HotelClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            HotelBookingSystem hotelBookingSystem = (HotelBookingSystem) registry.lookup("HotelBookingSystem");

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("1. Book Room");
                System.out.println("2. Cancel Booking");
                System.out.println("3. Check Availability");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                if (choice == 4) {
                    break;
                }
                switch (choice) {
                    case 1:
                        System.out.print("Enter your name: ");
                        String guestName = scanner.next();
                        System.out.print("Enter room number: ");
                        int roomNumber = scanner.nextInt();
                        boolean booked = hotelBookingSystem.bookRoom(guestName, roomNumber);
                        if (booked) {
                            System.out.println("Room booked successfully!");
                        } else {
                            System.out.println("Room is already booked.");
                        }
                        break;
                    case 2:
                        System.out.print("Enter your name: ");
                        guestName = scanner.next();
                        System.out.print("Enter room number: ");
                        roomNumber = scanner.nextInt();
                        boolean canceled = hotelBookingSystem.cancelBooking(guestName, roomNumber);
                        if (canceled) {
                            System.out.println("Booking canceled successfully!");
                        } else {
                            System.out.println("Booking cancellation failed.");
                        }
                        break;
                    case 3:
                        System.out.print("Enter room number: ");
                        roomNumber = scanner.nextInt();
                        String availability = hotelBookingSystem.checkAvailability(roomNumber);
                        System.out.println("Room " + roomNumber + " is " + availability);
                        break;
                }
            }

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
