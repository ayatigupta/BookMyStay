import java.util.*;

/**
 * ============================================================
 * MAIN CLASS - UseCase7AddOnServiceSelection
 * ============================================================
 *
 * Use Case 7: Add-On Service Selection
 *
 * Description:
 * Allows attaching optional services to reservations
 * without modifying booking or inventory logic.
 *
 * @author Developer
 * @version 7.1
 */
 class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        // Sample reservation IDs (from UC6)
        String reservationId1 = "R101";
        String reservationId2 = "R102";

        // Create service manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Add services to reservations
        manager.addService(reservationId1, new AddOnService("Breakfast", 200));
        manager.addService(reservationId1, new AddOnService("WiFi", 100));
        manager.addService(reservationId2, new AddOnService("Airport Pickup", 500));

        // Display services and cost
        manager.showServices(reservationId1);
        manager.showServices(reservationId2);
    }
}

/**
 * Add-On Service class
 */
class AddOnService {

    private String name;
    private double cost;

    public AddOnService(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }
}

/**
 * Add-On Service Manager
 */
class AddOnServiceManager {

    // Map: reservationId → list of services
    private HashMap<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add service
    public void addService(String reservationId, AddOnService service) {

        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added service: " + service.getName() +
                " to Reservation: " + reservationId);
    }

    // Show services + total cost
    public void showServices(String reservationId) {

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("\nNo services for " + reservationId);
            return;
        }

        double total = 0;

        System.out.println("\nServices for " + reservationId + ":");

        for (AddOnService s : services) {
            System.out.println("- " + s.getName() + " ₹" + s.getCost());
            total += s.getCost();
        }

        System.out.println("Total Add-On Cost: ₹" + total);
    }
}