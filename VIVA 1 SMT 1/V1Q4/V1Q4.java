import java.util.Scanner;

public class V1Q4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] names = {"Kampung Baru Hall", "Sri Muda School", "Masjid Al-Hidayah", "Taman Melati Hall"};
        int[] maxFamilies = {60, 80, 40, 50};
        double[] foodPerFamily = {3.0, 2.5, 4.0, 3.5};
        int[] distance = {12, 25, 8, 30};
        boolean[] served = new boolean[4];

        double food = 400;
        double fuel = 30;
        int totalFamilies = 0;
        double totalFood = 0;
        double totalFuel = 0;

        while (food > 0) {
            boolean canReach = false;
            for (int i = 0; i < 4; i++) {
                if (!served[i] && distance[i] * 2 * 0.25 <= fuel) {
                    canReach = true;
                }
            }
            if (!canReach) {
                System.out.println("No remaining centre can be reached with the fuel left.");
                break;
            }

            // ----- show menu -----
            System.out.println("===== Flood Relief Van Dispatcher =====");
            System.out.printf("Food Left: %.2f kg | Fuel Left: %.2f L%n", food, fuel);
            for (int i = 0; i < 4; i++) {
                System.out.printf("%d. %s (%d families, %.1f kg/family, %d km)", i + 1, names[i],
                        maxFamilies[i], foodPerFamily[i], distance[i]);
                if (served[i]) {
                    System.out.print(" [SERVED]");
                }
                System.out.println();
            }
            System.out.println("0. End dispatch");

            // ----- select centre -----
            System.out.print("Select a centre (0-4): ");
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter an integer from 0 to 4.");
                continue;
            }

            if (choice == 0) {
                System.out.println("Dispatch ended by user.");
                break;
            }
            if (choice < 0 || choice > 4) {
                System.out.println("Error: Invalid centre selection.");
                continue;
            }

            int c = choice - 1;
            if (served[c]) {
                System.out.println("Error: This centre has already been served.");
                continue;
            }

            double fuelNeeded = distance[c] * 2 * 0.25;
            if (fuelNeeded > fuel) {
                System.out.printf("Error: Not enough fuel to reach %s (needs %.2f L).%n", names[c], fuelNeeded);
                continue;
            }

            // ----- number of families -----
            int families = 0;
            boolean valid = false;
            while (!valid) {
                System.out.printf("Enter number of families to serve (1-%d): ", maxFamilies[c]);
                try {
                    families = Integer.parseInt(sc.nextLine().trim());
                    if (families < 1 || families > maxFamilies[c]) {
                        System.out.println("Error: Number of families is out of range.");
                    } else {
                        valid = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Enter a whole number.");
                }
            }

            // ----- food calculation -----
            double required = families * foodPerFamily[c];
            double delivered;
            int familiesServed;
            boolean foodRanOut = false;

            if (required <= food) {
                delivered = required;
                familiesServed = families;
            } else {
                familiesServed = (int) (food / foodPerFamily[c]);
                delivered = familiesServed * foodPerFamily[c];
                foodRanOut = true;
            }

            // ----- update values -----
            food -= delivered;
            fuel -= fuelNeeded;
            totalFood += delivered;
            totalFuel += fuelNeeded;
            totalFamilies += familiesServed;
            served[c] = true;

            // ----- delivery summary -----
            System.out.println("--- Delivery Summary ---");
            System.out.println("Centre: " + names[c]);
            System.out.printf("Food Required: %.2f kg%n", required);
            System.out.printf("Food Delivered: %.2f kg%n", delivered);
            System.out.printf("Fuel Used: %.2f L (round trip %d km)%n", fuelNeeded, distance[c] * 2);
            if (foodRanOut) {
                System.out.println("Food Ran Out! Only " + familiesServed + " of " + families + " families served.");
            } else {
                System.out.println("Result: " + familiesServed + " families served.");
            }
            System.out.println("------------------------");

            if (foodRanOut) {
                break;
            }
        }

        // ===== FINAL REPORT =====
        System.out.println("===== Dispatch Report =====");
        System.out.println("Families Served: " + totalFamilies);
        System.out.printf("Food Delivered: %.2f kg%n", totalFood);
        System.out.printf("Fuel Used: %.2f L%n", totalFuel);
        System.out.printf("Food Left: %.2f kg | Fuel Left: %.2f L%n", food, fuel);
    }
}
