import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExpenseSharingApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<User> users = new ArrayList<>();
    private static final List<Expense> expenses = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Welcome to the Expense Sharing App");
        System.out.println("---------------------------");

        char continueOption;
        do {
            showMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    createUserAccount();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    // Add expense related features
                    break;
                case 4:
                    // Add export feature
                    break;
                case 5:
                    // Add filtering feature
                    break;
                case 6:
                    // Add equally split feature
                    break;
                case 7:
                    // Add edit or delete feature
                    break;
                case 8:
                    // Add graphical representation feature
                    break;
                case 9:
                    System.out.println("Exiting the Advanced Expense Sharing App. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please choose again.");
                    break;
            }

            System.out.print("Do you want to perform another action? (y/n): ");
            continueOption = scanner.next().charAt(0);
            scanner.nextLine(); // Consume the newline character
        } while (continueOption == 'y' || continueOption == 'Y');

        scanner.close();
    }

    private static void showMainMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Create User Account");
        System.out.println("2. Login");
        System.out.println("3. Add Expense");
        System.out.println("4. Export to CSV/PDF");
        System.out.println("5. Expense Filtering");
        System.out.println("6. Equally Split Expenses");
        System.out.println("7. Edit or Delete Expense");
        System.out.println("8. Expense Summary Graphs");
        System.out.println("9. Exit");
        System.out.print("Choose an option (1-9): ");
    }

    private static void createUserAccount() {
        System.out.print("Enter a username: ");
        String username = scanner.nextLine();

        System.out.print("Enter preferred currency: ");
        String currency = scanner.nextLine();

        users.add(new User(username, currency));
        System.out.println("User account created successfully!");
    }

    private static void login() {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        User currentUser = findUser(username);

        if (currentUser != null) {
            System.out.println("Login successful. Welcome, " + currentUser.username + "!");
            // Additional functionalities for logged-in user
        } else {
            System.out.println("User not found. Please create an account.");
        }
    }

    private static User findUser(String username) {
        for (User user : users) {
            if (user.username.equals(username)) {
                return user;
            }
        }
        return null;
    }

    // Add more methods for expense-related features, exporting, filtering, splitting, editing, and graphing
}