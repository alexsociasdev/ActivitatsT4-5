import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class HotelServer {
    public static void main(String[] args) {
        try {
            HotelBookingSystem hotelBookingSystem = new HotelBookingSystemImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("HotelBookingSystem", hotelBookingSystem);
            System.out.println("Hotel booking system server ready.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
