import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HotelBookingSystem extends Remote {
    boolean bookRoom(String guestName, int roomNumber) throws RemoteException;
    boolean cancelBooking(String guestName, int roomNumber) throws RemoteException;
    String checkAvailability(int roomNumber) throws RemoteException;
}
