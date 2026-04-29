import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.*;

// ===============================
// MAIN CLASS (ALL IN ONE FILE)
// ===============================
public class HotelRMI {

    // ===============================
    // 1. REMOTE INTERFACE
    // ===============================
    public interface HotelService extends Remote {
        String bookRoom(String guestName) throws RemoteException;
        String cancelBooking(String guestName) throws RemoteException;
    }

    // ===============================
    // 2. SERVER IMPLEMENTATION
    // ===============================
    public static class HotelServiceImpl extends UnicastRemoteObject implements HotelService {
        private static final long serialVersionUID = 1L;

        HashMap<String, Integer> bookings = new HashMap<>();
        int roomNo = 1;

        protected HotelServiceImpl() throws RemoteException {
            super();
        }

        public String bookRoom(String guestName) throws RemoteException {
            if (bookings.containsKey(guestName)) {
                return "Already booked for " + guestName;
            }
            bookings.put(guestName, roomNo++);
            return "Room booked for " + guestName + ", Room No: " + bookings.get(guestName);
        }

        public String cancelBooking(String guestName) throws RemoteException {
            if (!bookings.containsKey(guestName)) {
                return "No booking found for " + guestName;
            }
            bookings.remove(guestName);
            return "Booking cancelled for " + guestName;
        }
    }

    // ===============================
    // 3. SERVER START
    // ===============================
    public static class Server {
        public static void start() {
            try {
                HotelService service = new HotelServiceImpl();

                Registry registry = LocateRegistry.createRegistry(1099);
                registry.rebind("HotelService", service);

                System.out.println("🏨 Server Started...");
                System.out.println("Server is running. Press Ctrl+C to stop.");
                
                // Keep the server running
                synchronized (Server.class) {
                    Server.class.wait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // ===============================
    // 4. CLIENT
    // ===============================
    public static class Client {
        public static void start() {
            Scanner sc = new Scanner(System.in);
            try {
                Registry registry = LocateRegistry.getRegistry("localhost", 1099);
                HotelService service = (HotelService) registry.lookup("HotelService");

                while (true) {
                    System.out.println("\n1. Book Room");
                    System.out.println("2. Cancel Booking");
                    System.out.println("3. Exit");
                    System.out.print("Enter choice: ");

                    int choice = sc.nextInt();
                    sc.nextLine();

                    if (choice == 1) {
                        System.out.print("Enter name: ");
                        String name = sc.nextLine();
                        System.out.println(service.bookRoom(name));

                    } else if (choice == 2) {
                        System.out.print("Enter name: ");
                        String name = sc.nextLine();
                        System.out.println(service.cancelBooking(name));

                    } else {
                        break;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                sc.close();
            }
        }
    }

    // ===============================
    // 5. MAIN METHOD
    // ===============================
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("1. Start Server");
            System.out.println("2. Start Client");
            System.out.print("Choose: ");

            int choice = sc.nextInt();

            if (choice == 1) {
                Server.start();
            } else {
                Client.start();
            }
        } finally {
            sc.close();
        }
    }
}