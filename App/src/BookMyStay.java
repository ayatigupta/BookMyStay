import java.io.*;
import java.util.*;

/**
 * Use Case 12: Data Persistence & System Recovery
 */
class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        PersistenceService service = new PersistenceService();

        // Simulate system start (load data)
        SystemState state = service.loadState();

        // If no previous state → initialize
        if (state == null) {
            state = new SystemState();
            state.inventory.put("Single", 2);
            state.bookings.put("R101", "Single");
        }

        System.out.println("Current State:");
        System.out.println("Inventory: " + state.inventory);
        System.out.println("Bookings: " + state.bookings);

        // Simulate new booking
        state.bookings.put("R102", "Single");
        state.inventory.put("Single", state.inventory.get("Single") - 1);

        // Save state before shutdown
        service.saveState(state);

        System.out.println("\nState saved successfully!");
    }
}

/**
 * System State (Serializable)
 */
class SystemState implements Serializable {

    HashMap<String, Integer> inventory = new HashMap<>();
    HashMap<String, String> bookings = new HashMap<>();
}

/**
 * Persistence Service
 */
class PersistenceService {

    private final String FILE_NAME = "system_state.dat";

    // Save state
    public void saveState(SystemState state) {

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);

        } catch (IOException e) {
            System.out.println("Error saving data!");
        }
    }

    // Load state
    public SystemState loadState() {

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            return (SystemState) ois.readObject();

        } catch (Exception e) {
            System.out.println("No previous data found. Starting fresh...");
            return null;
        }
    }
}