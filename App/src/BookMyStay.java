import java.util.*;

/**
 * Use Case 11: Concurrent Booking Simulation
 */
class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        // Shared booking queue
        Queue<Reservation> queue = new LinkedList<>();

        // Add multiple requests
        queue.offer(new Reservation("Alice", "Single"));
        queue.offer(new Reservation("Bob", "Single"));
        queue.offer(new Reservation("Charlie", "Single"));
        queue.offer(new Reservation("David", "Single"));

        // Create threads (simulate multiple users)
        Thread t1 = new Thread(new BookingProcessor(queue, inventory));
        Thread t2 = new Thread(new BookingProcessor(queue, inventory));

        t1.start();
        t2.start();
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
 * Inventory (shared resource)
 */
class RoomInventory {

    private HashMap<String, Integer> map = new HashMap<>();

    public RoomInventory() {
        map.put("Single", 2); // limited stock
    }

    public synchronized boolean allocateRoom(String type) {

        int available = map.getOrDefault(type, 0);

        if (available > 0) {
            map.put(type, available - 1);
            return true;
        }

        return false;
    }
}

/**
 * Booking Processor (Runnable)
 */
class BookingProcessor implements Runnable {

    private Queue<Reservation> queue;
    private RoomInventory inventory;

    public BookingProcessor(Queue<Reservation> queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {

        while (true) {

            Reservation r;

            // Critical section: accessing shared queue
            synchronized (queue) {
                if (queue.isEmpty()) break;
                r = queue.poll();
            }

            // Critical section: inventory update
            boolean success = inventory.allocateRoom(r.roomType);

            if (success) {
                System.out.println(Thread.currentThread().getName() +
                        " → Booking Confirmed: " + r.guestName);
            } else {
                System.out.println(Thread.currentThread().getName() +
                        " → Booking Failed: " + r.guestName);
            }
        }
    }
}