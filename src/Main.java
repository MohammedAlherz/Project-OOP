import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    /// This is the main class for the Car Pooling System
    private static Scanner input = new Scanner(System.in);
    // Random instance to generate random IDs and car codes
    private static Random random = new Random();
    // Lists to hold available routes and cars
    private static ArrayList<Route> availableRoutes = new ArrayList<>();
    // List to hold available cars
    private static ArrayList<Car> availableCars = new ArrayList<>();

    public static void main(String[] args) {
        // Initialize system data
        initializeSystem();

        // Run the required test cases first
        runTestCases();

        // Run the interactive system
        runInteractiveSystem();
    }

    /**
     * Initializes the system with predefined routes.
     * This method is called at the start of the program to set up the initial state.
     */
    private static void initializeSystem() {
        // Create Routes
        availableRoutes.add(new Route("Al Ahsa", "Dammam", 100));
        availableRoutes.add(new Route("Al Ahsa", "Riyadh", 150));
        availableRoutes.add(new Route("Dammam", "Al Ahsa", 100));
        availableRoutes.add(new Route("Riyadh", "Al Ahsa", 150));

        System.out.println("=== Car Pooling System Initialized ===");
    }

    /**
     * Runs predefined test cases to validate the functionality of the system.
     * This method is called before starting the interactive system.
     */
    private static void runTestCases() {
        System.out.println("\n=== RUNNING TEST CASES ===");

        Route route1 = new Route("Al Ahsa", "Dammam", 100);
        Route route2 = new Route("Al Ahsa", "Riyadh", 150);

        Car car1 = new Car("C100", route1, 3);
        Car car2 = new Car("C200", route2, 0);

        Passenger[] passengers = new Passenger[2];
        passengers[0] = new SubscriberPassenger("Hussain", "101", car1, route1.getPrice());
        passengers[1] = new NonSubscriberPassenger("Ahmed", "102", car2, route2.getPrice(), true);

        try {
            passengers[0].reservedCar(car1);
            System.out.println("✓ Subscriber passenger reserved car successfully");
        } catch (Exception e) {
            System.out.println("✗ Error reserving car for subscriber: " + e.getMessage());
        }

        try {
            passengers[1].reservedCar(car2);
            System.out.println("✗ Non-subscriber reservation should have failed");
        } catch (Exception e) {
            System.out.println("✓ Expected exception for full car: " + e.getMessage());
        }

        System.out.println("\n=== PASSENGER INFORMATION ===");
        for (Passenger p : passengers) {
            p.displayInfo();
            System.out.println("-----------------------");
        }
    }

    /**
     * Runs the interactive booking system.
     * This method allows users to book rides, select routes, and cars interactively.
     */
    private static void runInteractiveSystem() {
        System.out.println("\n=== INTERACTIVE BOOKING SYSTEM ===");
        System.out.println("Welcome to the Car Pooling System!");

        while (true) {
            try {
                String name = getPassengerName();
                String id = generatePassengerId();

                Route selectedRoute = selectRoute();
                if (selectedRoute == null) continue;

                generateCarsForRoute(selectedRoute);

                Car selectedCar = chooseCar();
                if (selectedCar == null) continue;

                Passenger passenger = createPassenger(name, id, selectedCar, selectedRoute);
                if (passenger == null) continue;

                System.out.println("\n=== BOOKING CONFIRMATION ===");
                passenger.reservedCar(selectedCar);
                passenger.displayInfo();
                System.out.println("Thank you for using the Car Pooling System!");
                break;

            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                System.out.println("Please try again.");
            }
        }
    }

    /**
     * Prompts the user to enter their name.
     * @return The name entered by the user.
     */
    private static String getPassengerName() {
        System.out.println("\nPlease enter your name:");
        return input.nextLine().trim();
    }

    /**
     * Generates a random passenger ID.
     * The ID is in the format "IDXXXX" where XXXX is a random number between 1000 and 9999.
     * @return The generated passenger ID.
     */
    private static String generatePassengerId() {
        String id = "ID" + (1000 + random.nextInt(9000));
        System.out.println("Your generated ID is: " + id);
        return id;
    }

    /**
     * Prompts the user to select a route from the available routes.
     * @return The selected Route object.
     */
    private static Route selectRoute() {
        while (true) {
            try {
                System.out.println("\nPlease choose a route:");
                for (int i = 0; i < availableRoutes.size(); i++) {
                    Route route = availableRoutes.get(i);
                    System.out.println((i + 1) + ". " + route.getStartAddress() +
                            " to " + route.getDestinationAddress() +
                            " - Price: " + route.getPrice() + " SAR");
                }

                System.out.print("Enter your choice (1-" + availableRoutes.size() + "): ");
                int choice = input.nextInt();
                input.nextLine(); // Consume newline

                if (choice >= 1 && choice <= availableRoutes.size()) {
                    return availableRoutes.get(choice - 1);
                } else {
                    System.out.println("Invalid choice. Please select a valid number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                input.nextLine(); // Clear invalid input
            }
        }
    }

    /**
     * Generates a list of cars for the selected route.
     * This method creates three cars, one of which is full.
     * @param route The selected Route object.
     */
    private static void generateCarsForRoute(Route route) {
        availableCars.clear();

        for (int i = 0; i < 3; i++) {
            String carCode = "C" + (100 + random.nextInt(900));
            int capacity = (i == 0) ? 0 : random.nextInt(4) + 1;
            availableCars.add(new Car(carCode, route, capacity));
        }
    }

    /**
     * Prompts the user to choose a car from the available cars.
     * Displays the status of each car (available or full).
     * @return The selected Car object, or null if the user chooses to go back.
     */
    private static Car chooseCar() {
        while (true) {
            System.out.println("\n=== AVAILABLE CARS ===");
            for (int i = 0; i < availableCars.size(); i++) {
                Car car = availableCars.get(i);
                String status = car.getMaxCapacity() > 0 ? "Available" : "Full";
                System.out.printf("%d. %s - Seats: %d (%s)%n",
                        i + 1, car.getCode(), car.getMaxCapacity(), status);
            }

            System.out.printf("%d. Go back to route selection%n", availableCars.size() + 1);
            System.out.print("Enter your choice: ");

            try {
                int choice = input.nextInt();
                input.nextLine(); // clear newline

                if (choice >= 1 && choice <= availableCars.size()) {
                    Car selected = availableCars.get(choice - 1);
                    if (selected.getMaxCapacity() == 0) {
                        System.out.println("✗ Car is full, please choose another one.");
                    } else {
                        return selected;
                    }
                } else if (choice == availableCars.size() + 1) {
                    return null;
                } else {
                    System.out.println("Invalid choice. Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number.");
                input.nextLine(); // clear invalid input
            }
        }
    }

    /**
     * Creates a Passenger object based on user input.
     * Prompts the user to subscribe for a discount or continue as a guest.
     * @param name The name of the passenger.
     * @param id The ID of the passenger.
     * @param car The selected Car object.
     * @param route The selected Route object.
     * @return A Passenger object (either SubscriberPassenger or NonSubscriberPassenger).
     */
    private static Passenger createPassenger(String name, String id, Car car, Route route) {
        while (true) {
            try {
                System.out.println("\nWould you like to subscribe to our system for a 50% discount?");
                System.out.println("1. Yes (Subscribe)");
                System.out.println("2. No (Continue as guest)");
                System.out.print("Enter your choice: ");

                int choice = input.nextInt();
                input.nextLine(); // Consume newline

                if (choice == 1) {
                    System.out.println("✓ You have subscribed successfully! You will get a 50% discount.");
                    return new SubscriberPassenger(name, id, car, route.getPrice());
                } else if (choice == 2) {
                    boolean hasCoupon = askForCoupon();
                    if (hasCoupon) {
                        System.out.println("✓ Coupon applied! You will get a 10% discount.");
                    } else {
                        System.out.println("No discount applied. Standard price: " + route.getPrice() + " SAR");
                    }
                    return new NonSubscriberPassenger(name, id, car, route.getPrice(), hasCoupon);
                } else {
                    System.out.println("Invalid choice. Please enter 1 or 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                input.nextLine(); // Clear invalid input
            }
        }
    }

    /**
     * Asks the user if they have a discount coupon.
     * @return true if the user has a coupon, false otherwise.
     */
    private static boolean askForCoupon() {
        while (true) {
            System.out.print("Do you have a discount coupon? (yes/no): ");
            String response = input.nextLine().trim().toLowerCase();

            if (response.equals("yes") || response.equals("y")) {
                return true;
            } else if (response.equals("no") || response.equals("n")) {
                return false;
            } else {
                System.out.println("Please enter 'yes' or 'no'.");
            }
        }
    }
}
