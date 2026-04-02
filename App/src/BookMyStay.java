import java.util.*;

/**
 * ============================================================
 * MAIN CLASS - UseCase8BookingHistoryReport
 * ============================================================
 *
 * Use Case 8: Booking History & Reporting
 *
 * Description:
 * Stores confirmed bookings and generates reports.
 *
 * @author Developer
 * @version 8.1
 */
 class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        // Booking history
        BookingHistory history = new BookingHistory();

        // Add confirmed bookings (simulate UC6 output)
        history.addBooking(new Reservation("R101", "Alice", "Single"));
        history.addBooking(new Reservation("R102", "Bob", "Double"));
        history.addBooking(new Reservation("R103", "Charlie", "Suite"));

        // Report service
        BookingReportService report = new BookingReportService();

        // Show all bookings
        report.showAllBookings(history);

        // Show summary
        report.generateSummary(history);
    }
}

/**
 * Reservation class
 */
class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String id, String guest, String type) {
        this.reservationId = id;
        this.guestName = guest;
        this.roomType = type;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/**
 * Booking History (stores data)
 */
class BookingHistory {

    // List preserves order
    private List<Reservation> history = new ArrayList<>();

    public void addBooking(Reservation r) {
        history.add(r);
    }

    public List<Reservation> getAllBookings() {
        return history;
    }
}

/**
 * Reporting Service (read-only)
 */
class BookingReportService {

    // Show all bookings
    public void showAllBookings(BookingHistory history) {

        System.out.println("===== Booking History =====");

        for (Reservation r : history.getAllBookings()) {
            System.out.println(
                    r.getReservationId() + " | " +
                            r.getGuestName() + " | " +
                            r.getRoomType()
            );
        }
    }

    // Generate summary
    public void generateSummary(BookingHistory history) {

        HashMap<String, Integer> countMap = new HashMap<>();

        for (Reservation r : history.getAllBookings()) {
            String type = r.getRoomType();
            countMap.put(type, countMap.getOrDefault(type, 0) + 1);
        }

        System.out.println("\n===== Booking Summary =====");

        for (String type : countMap.keySet()) {
            System.out.println(type + " Rooms Booked: " + countMap.get(type));
        }
    }
}