import java.util.*;

/**
 * ============================================================
 * MAIN CLASS - UseCase5BookingRequestQueue
 * ============================================================
 *
 * Use Case 5: Booking Request Queue (FIFO)
 *
 * Description:
 * Demonstrates fair booking request handling using Queue.
 *
 * @author Developer
 * @version 5.1
 */ class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        // Create booking queue
        BookingQueue queue = new BookingQueue();

        // Add booking requests
        queue.addRequest(new Reservation("Alice", "Single"));
        queue.addRequest(new Reservation("Bob", "Double"));
        queue.addRequest(new Reservation("Charlie", "Suite"));

        // Display queue
        queue.showQueue();
    }
}

/**
 * Reservation class (represents booking request)
 */
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/**
 * Booking Queue (FIFO)
 */
class BookingQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    // Add request
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added: " +
                reservation.getGuestName() + " → " + reservation.getRoomType());
    }

    // Display queue (no processing yet)
    public void showQueue() {

        System.out.println("\n===== Booking Queue (FIFO) =====");

        for (Reservation r : queue) {
            System.out.println(r.getGuestName() + " → " + r.getRoomType());
        }
    }
}