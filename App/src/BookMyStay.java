import java.util.*;

/**
 * Use Case 10: Booking Cancellation & Inventory Rollback
 */
class UseCase10BookingCancellation {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingManager manager = new BookingManager(inventory);

        // Confirm bookings (simulate UC6)
        manager.confirmBooking("R101", "Single");
        manager.confirmBooking("R102", "Single");

        // Cancel booking
        manager.cancelBooking("R102");

        // Try invalid cancellation
        manager.cancelBooking("R999");
    }
}

/**
 * Inventory Service
 */
class RoomInventory {

    private HashMap<String, Integer> map = new HashMap<>();

    public RoomInventory() {
        map.put("Single", 1);
    }

    public int getAvailability(String type) {
        return map.getOrDefault(type, 0);
    }

    public void decrement(String type) {
        map.put(type, map.get(type) - 1);
    }

    public void increment(String type) {
        map.put(type, map.get(type) + 1);
    }
}

/**
 * Booking Manager
 */
class BookingManager {

    private RoomInventory inventory;

    // reservationId → roomType
    private HashMap<String, String> bookings = new HashMap<>();

    // Stack for rollback (LIFO)
    private Stack<String> rollbackStack = new Stack<>();

    public BookingManager(RoomInventory inventory) {
        this.inventory = inventory;
    }

    // Confirm booking
    public void confirmBooking(String reservationId, String roomType) {

        if (inventory.getAvailability(roomType) > 0) {

            inventory.decrement(roomType);
            bookings.put(reservationId, roomType);

            System.out.println("Booking Confirmed: " + reservationId);

        } else {
            System.out.println("No rooms available for " + roomType);
        }
    }

    // Cancel booking
    public void cancelBooking(String reservationId) {

        // Validate booking
        if (!bookings.containsKey(reservationId)) {
            System.out.println("Cancellation Failed: Invalid reservation ID");
            return;
        }

        String roomType = bookings.get(reservationId);

        // Push to rollback stack
        rollbackStack.push(reservationId);

        // Restore inventory
        inventory.increment(roomType);

        // Remove booking
        bookings.remove(reservationId);

        System.out.println("Booking Cancelled: " + reservationId);
    }
}