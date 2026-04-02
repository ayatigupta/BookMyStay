import java.util.*;

/**
 * ============================================================
 * MAIN CLASS - UseCase6RoomAllocationService
 * ============================================================
 *
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Description:
 * Processes booking queue, assigns unique room IDs,
 * updates inventory, and prevents double booking.
 *
 * @author Developer
 * @version 6.1
 */
 class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Create booking queue
        Queue<Reservation> queue = new LinkedList<>();
        queue.offer(new Reservation("Alice", "Single"));
        queue.offer(new Reservation("Bob", "Single"));
        queue.offer(new Reservation("Charlie", "Suite"));

        // Booking service
        BookingService service = new BookingService(inventory);

        // Process queue
        while (!queue.isEmpty()) {
            service.processBooking(queue.poll());
        }
    }
}

/**
 * Reservation class
 */
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

/**
 * Inventory Service (same as UC3)
 */
class RoomInventory {

    private HashMap<String, Integer> map = new HashMap<>();

    public RoomInventory() {
        map.put("Single", 2);
        map.put("Double", 1);
        map.put("Suite", 1);
    }

    public int getAvailability(String type) {
        return map.getOrDefault(type, 0);
    }

    public void decrement(String type) {
        map.put(type, map.get(type) - 1);
    }
}

/**
 * Booking Service (core logic)
 */
class BookingService {

    private RoomInventory inventory;

    // Track allocated room IDs
    private Set<String> allocatedRooms = new HashSet<>();

    // Map roomType -> allocated IDs
    private HashMap<String, Set<String>> allocationMap = new HashMap<>();

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processBooking(Reservation r) {

        int available = inventory.getAvailability(r.roomType);

        if (available > 0) {

            // Generate unique room ID
            String roomId = generateRoomId(r.roomType);

            // Ensure uniqueness
            while (allocatedRooms.contains(roomId)) {
                roomId = generateRoomId(r.roomType);
            }

            allocatedRooms.add(roomId);

            // Map room type → room IDs
            allocationMap.putIfAbsent(r.roomType, new HashSet<>());
            allocationMap.get(r.roomType).add(roomId);

            // Update inventory
            inventory.decrement(r.roomType);

            // Confirm booking
            System.out.println("Booking Confirmed: " +
                    r.guestName + " → " + r.roomType +
                    " | Room ID: " + roomId);

        } else {
            System.out.println("Booking Failed: " +
                    r.guestName + " → No rooms available");
        }
    }

    // Generate room ID
    private String generateRoomId(String type) {
        return type.substring(0, 1).toUpperCase() + new Random().nextInt(1000);
    }
}