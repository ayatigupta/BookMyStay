import java.util.*;

/**
 * ============================================================
 * MAIN CLASS - UseCase4RoomSearch
 * ============================================================
 *
 * Use Case 4: Room Search & Availability Check
 *
 * Description:
 * Demonstrates read-only search of available rooms without
 * modifying inventory state.
 *
 * @author Developer
 * @version 4.1
 */
class UseCase4RoomSearch {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Create room objects (domain model)
        List<Room> rooms = Arrays.asList(
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        );

        // Perform search
        SearchService searchService = new SearchService();
        searchService.searchAvailableRooms(rooms, inventory);
    }
}

/**
 * Search Service (Read-Only)
 */
class SearchService {

    public void searchAvailableRooms(List<Room> rooms, RoomInventory inventory) {

        System.out.println("===== Available Rooms =====");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getType());

            // Show only available rooms
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available);
                System.out.println();
            }
        }
    }
}

/**
 * Inventory class (same as UC3)
 */
class RoomInventory {

    private HashMap<String, Integer> map = new HashMap<>();

    public RoomInventory() {
        map.put("Single", 5);
        map.put("Double", 0); // unavailable to test filtering
        map.put("Suite", 2);
    }

    public int getAvailability(String type) {
        return map.getOrDefault(type, 0);
    }
}

/**
 * Abstract Room class
 */
abstract class Room {

    protected int beds;
    protected String size;
    protected double price;

    public Room(int beds, String size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public abstract void displayDetails();

    public abstract String getType();
}

/**
 * Single Room
 */
class SingleRoom extends Room {

    public SingleRoom() {
        super(1, "Small", 1000);
    }

    public void displayDetails() {
        System.out.println("Room: Single | Beds: " + beds +
                " | Size: " + size + " | Price: ₹" + price);
    }

    public String getType() {
        return "Single";
    }
}

/**
 * Double Room
 */
class DoubleRoom extends Room {

    public DoubleRoom() {
        super(2, "Medium", 2000);
    }

    public void displayDetails() {
        System.out.println("Room: Double | Beds: " + beds +
                " | Size: " + size + " | Price: ₹" + price);
    }

    public String getType() {
        return "Double";
    }
}

/**
 * Suite Room
 */
class SuiteRoom extends Room {

    public SuiteRoom() {
        super(3, "Large", 5000);
    }

    public void displayDetails() {
        System.out.println("Room: Suite | Beds: " + beds +
                " | Size: " + size + " | Price: ₹" + price);
    }

    public String getType() {
        return "Suite";
    }
}