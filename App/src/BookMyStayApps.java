import java.util.*;

/**
 * UseCase4RoomSearch
 *
 * This class demonstrates read-only room search functionality.
 * It retrieves availability from centralized inventory and
 * displays only available room types without modifying system state.
 *
 * Version 4.0 – Room Search & Availability Check
 *
 * @author Sri
 * @version 4.0
 */
public class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("==============================================");
        System.out.println("        Welcome to Book My Stay");
        System.out.println("  Hotel Booking Management System v4.0");
        System.out.println("==============================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 10);
        inventory.addRoomType("Double Room", 0); // unavailable
        inventory.addRoomType("Suite Room", 2);

        // Initialize room objects (domain model)
        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom(1, 180.0, 2500.00));
        rooms.add(new DoubleRoom(2, 250.0, 4000.00));
        rooms.add(new SuiteRoom(3, 450.0, 7500.00));

        // Perform search (read-only)
        RoomSearchService searchService = new RoomSearchService();
        searchService.displayAvailableRooms(rooms, inventory);

        System.out.println("\nApplication terminated successfully.");
    }
}


/**
 * RoomSearchService
 *
 * Handles read-only operations for searching available rooms.
 */
class RoomSearchService {

    public void displayAvailableRooms(List<Room> rooms, RoomInventory inventory) {

        System.out.println("\nAvailable Rooms:\n");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            // Defensive check: show only available rooms
            if (available > 0) {
                room.displayRoomDetails();
                System.out.println("Available Units: " + available);
                System.out.println("--------------------------------------");
            }
        }
    }
}


/**
 * RoomInventory (Same centralized inventory concept)
 */
class RoomInventory {

    private Map<String, Integer> availabilityMap;

    public RoomInventory() {
        availabilityMap = new HashMap<>();
    }

    public void addRoomType(String roomType, int count) {
        availabilityMap.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return availabilityMap.getOrDefault(roomType, 0);
    }
}


/**
 * Abstract Room Class
 */
abstract class Room {

    private int numberOfBeds;
    private double sizeInSqFt;
    private double pricePerNight;

    public Room(int beds, double size, double price) {
        this.numberOfBeds = beds;
        this.sizeInSqFt = size;
        this.pricePerNight = price;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public double getSizeInSqFt() {
        return sizeInSqFt;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public abstract String getRoomType();

    public void displayRoomDetails() {
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size (sq ft): " + sizeInSqFt);
        System.out.println("Price per Night: ₹" + pricePerNight);
    }
}


/**
 * Single Room Implementation
 */
class SingleRoom extends Room {

    public SingleRoom(int beds, double size, double price) {
        super(beds, size, price);
    }

    @Override
    public String getRoomType() {
        return "Single Room";
    }
}


/**
 * Double Room Implementation
 */
class DoubleRoom extends Room {

    public DoubleRoom(int beds, double size, double price) {
        super(beds, size, price);
    }

    @Override
    public String getRoomType() {
        return "Double Room";
    }
}


/**
 * Suite Room Implementation
 */
class SuiteRoom extends Room {

    public SuiteRoom(int beds, double size, double price) {
        super(beds, size, price);
    }

    @Override
    public String getRoomType() {
        return "Suite Room";
    }
}