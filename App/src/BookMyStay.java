import java.util.HashMap;
import java.util.Map;

/**
 * ============================================================
 * MAIN CLASS - UseCase3InventorySetup
 * ============================================================
 *
 * Use Case 3: Centralized Room Inventory Management
 *
 * Description:
 * Demonstrates centralized inventory using HashMap.
 *
 * @author Developer
 * @version 3.1
 */
class UseCase3InventorySetup {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display inventory
        System.out.println("===== Room Inventory =====");
        inventory.displayInventory();

        // Update availability
        System.out.println("\nUpdating Single Room availability...");
        inventory.updateAvailability("Single", 4);

        // Display updated inventory
        System.out.println("\n===== Updated Inventory =====");
        inventory.displayInventory();
    }
}

/**
 * Inventory class managing room availability
 */
class RoomInventory {

    private HashMap<String, Integer> availabilityMap;

    // Constructor initializes inventory
    public RoomInventory() {
        availabilityMap = new HashMap<>();

        availabilityMap.put("Single", 5);
        availabilityMap.put("Double", 3);
        availabilityMap.put("Suite", 2);
    }

    // Get availability
    public int getAvailability(String roomType) {
        return availabilityMap.getOrDefault(roomType, 0);
    }

    // Update availability
    public void updateAvailability(String roomType, int count) {
        availabilityMap.put(roomType, count);
    }

    // Display all inventory
    public void displayInventory() {
        for (Map.Entry<String, Integer> entry : availabilityMap.entrySet()) {
            System.out.println("Room Type: " + entry.getKey() +
                    " | Available: " + entry.getValue());
        }
    }
}