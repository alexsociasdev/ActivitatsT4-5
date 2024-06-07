import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class HotelBookingSystemImpl extends UnicastRemoteObject implements HotelBookingSystem {
    private Map<Integer, String> bookings;

    public HotelBookingSystemImpl() throws RemoteException {
        bookings = new HashMap<>();
    }

    @Override
    public synchronized boolean bookRoom(String guestName, int roomNumber) throws RemoteException {
        if (bookings.containsKey(roomNumber)) {
            return false;
        } else {
            bookings.put(roomNumber, guestName);
            return true;
        }
    }

    @Override
    public synchronized boolean cancelBooking(String guestName, int roomNumber) throws RemoteException {
        if (bookings.containsKey(roomNumber) && bookings.get(roomNumber).equals(guestName)) {
            bookings.remove(roomNumber);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public synchronized String checkAvailability(int roomNumber) throws RemoteException {
        return bookings.getOrDefault(roomNumber, "Available");
    }
}
