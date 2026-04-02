import java.util.*;

/**
 * ============================================================
 * MAIN CLASS - UseCase9ErrorHandlingValidation
 * ============================================================
 *
 * Use Case 9: Error Handling & Validation
 *
 * Description:
 * Demonstrates validation and custom exception handling
 * to ensure system reliability.
 *
 * @author Developer
 * @version 9.1
 */
 class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        BookingService service = new BookingService(inventory);

        try {
            // Valid booking
            service.bookRoom("Alice", "Single");

            // Invalid room type
            service.bookRoom("Bob", "Luxury");

            // Exhaust stock
            service.bookRoom("Charlie", "Single");
            service.bookRoom("David", "Single"); // should fail

        } catch (BookingException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\nSystem continues running safely...");
    }
}

/**
 * Custom Exception
 */
class BookingException extends Exception {
    public BookingException(String message) {
        super(message);
    }
}

/**
 * Inventory Service
 */
class RoomInventory {

    private HashMap<String, Integer> map = new HashMap<>();

    public RoomInventory() {
        map.put("Single", 2);
        map.put("Double", 1);
        map.put("Suite", 1);
    }

    public boolean isValidRoomType(String type) {
        return map.containsKey(type);
    }

    public int getAvailability(String type) {
        return map.getOrDefault(type, 0);
    }

    public void decrement(String type) {
        map.put(type, map.get(type) - 1);
    }
}

/**
 * Booking Service with validation
 */
class BookingService {

    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void bookRoom(String guest, String roomType) throws BookingException {

        // Validate room type
        if (!inventory.isValidRoomType(roomType)) {
            throw new BookingException("Invalid room type: " + roomType);
        }

        // Validate availability
        if (inventory.getAvailability(roomType) <= 0) {
            throw new BookingException("No rooms available for type: " + roomType);
        }

        // Safe update
        inventory.decrement(roomType);

        System.out.println("Booking successful: " +
                guest + " → " + roomType);
    }
}