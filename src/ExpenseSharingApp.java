import java.io.FileWriter; // For exporting to CSV
import java.io.IOException; // For handling IO exceptions
import java.text.ParseException; // For handling date parsing exceptions
import java.text.SimpleDateFormat; // For formatting dates
import java.util.ArrayList; // For storing users and expenses
import java.util.Date; // For storing dates
import java.util.List; // For storing users and expenses
import java.util.Scanner;

public class ExpenseSharingApp {
    private static final Scanner userInput = new Scanner(System.in);
    private static final List<User> users = new ArrayList<>(); // List of users
    private static final List<Expense> expenses = new ArrayList<>(); // List of expenses
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        System.out.println("Welcome to the Expense Sharing App");
        System.out.println("---------------------------");

        char continueOption; // To store the user's choice to continue or exit
        do {
            showMainMenu(); // Display the main menu
            int choice = userInput.nextInt();
            userInput.nextLine(); // Consume the newline character

            switch (choice) {
                case 1: // For Create User Account
                    createUserAccount();
                    break;
                case 2: // For Login
                    login();
                    break;
                case 3: // For Add Expense
                    addExpense();
                    break;
                case 4: // For Export to CSV/PDF
                    exportToCSV();
                    break;
                case 5: // For Expense Filtering
                    filterExpenses();
                    break;
                case 6: // For Equally Split Expenses
                    equallySplitExpenses();
                    break;
                case 7: // For Edit or Delete Expense
                    editOrDeleteExpense();
                    break;
                case 8: // For Expense Summary Graphs
                    expenseSummaryGraphs();
                    break;
                case 9: // For Exit
                    System.out.println("Exiting the Expense Sharing App. Goodbye!");
                    return;
                default: // For invalid choice
                    System.out.println("Invalid choice. Please choose again.");
                    break;
            }

            // Prompt the user to continue or exit
            System.out.print("Do you want to perform another action? (y/n): ");
            continueOption = userInput.next().charAt(0); // Read the first character of the input
            userInput.nextLine(); // Consume the newline character
        } while (continueOption == 'y' || continueOption == 'Y');
        userInput.close(); // Close the Scanner
    }

    // Display the main menu
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

    // Create a new user account and add it to the list of users
    private static void createUserAccount() {
        System.out.print("Enter a username: ");
        String username = userInput.nextLine();

        System.out.print("Enter preferred currency: ");
        String currency = userInput.nextLine();

        // Add the new user to the list of users
        users.add(new User(username, currency));
        System.out.println("User account created successfully!");

        // Additional functionalities for logged-in user

        // Display the main menu
        showMainMenu();
        // Prompt the user to continue or exit
        System.out.print("Do you want to login? (y/n): ");
        char loginOption = userInput.next().charAt(0); // Read the first character of the input
        userInput.nextLine(); // Consume the newline character

        if (loginOption == 'y' || loginOption == 'Y') {
            login();
        } else {
            System.out.println("Returning to the main menu.");
        }
    }

    // Login to an existing user account
    private static void login() {
        System.out.print("Enter your username: ");
        String username = userInput.nextLine();

        // Find the user with the given username in the list of users
        User currentUser = findUser(username);

        // If the user exists, currentUser will be non-null
        if (currentUser != null) {
            System.out.println("Login successful. Welcome, " + currentUser.username + "!");
            // Additional functionalities for logged-in user

            // Display the main menu
            showMainMenu();
            // Prompt the user to continue or exit
            System.out.print("Do you want to continue? (y/n): ");
            char continueOption = userInput.next().charAt(0); // Read the first character of the input
            userInput.nextLine(); // Consume the newline character

            if (continueOption == 'y' || continueOption == 'Y') {
                // Continue with the additional functionalities for logged-in user

                // Display the main menu
                showMainMenu();
                // Prompt the user to continue or exit
                System.out.print("Do you want to continue? (y/n): ");
                char continueOption2 = userInput.next().charAt(0); // Read the first character of the input
                userInput.nextLine(); // Consume the newline character

                if (continueOption2 == 'y' || continueOption2 == 'Y') {
                    // Continue with the additional functionalities for logged-in user
                    System.out.println("Returning to the main menu.");
                } else {
                    System.out.println("Exiting the Expense Sharing App. Goodbye!");
                }
            } else {
                System.out.println("Returning to the main menu.");
            }
        } else {
            System.out.println("User not found. Please create an account.");
        }
    }

    // Find the user with the given username in the list of users
    private static User findUser(String username) {
        for (User user : users) { // For each user in the list of users, check if the username matches,
            // and return the user if found
            // (a return statement terminates the method)
            // Check if the username matches the given username
            if (user.username.equals(username)) {
                return user;
            }
        }
        return null; // If no user is found, return null
    }

    // Add a new expense to the list of expenses and update the balances of the users
    private static void addExpense() {
        System.out.print("\nEnter the name of the person incurring the expense: ");
        String person = userInput.nextLine();

        // Check if the person exists in the list of users
        if (userExists(person)) {
            System.out.println("Invalid person. Please enter a valid name.");
            return;
        }

        System.out.print("Enter the expense amount for " + person + ": ");
        double amount = userInput.nextDouble(); // Read the amount
        userInput.nextLine(); // Consume the newline character

        System.out.print("Enter the expense category: ");
        String category = userInput.nextLine(); // Read the category

        System.out.print("Enter the date of the expense (YYYY-MM-DD): ");
        String dateString = userInput.nextLine();
        Date date = parseDate(dateString); // Parse the date string to a Date object

        System.out.print("Enter a comment for the expense: ");
        String comment = userInput.nextLine();

        expenses.add(new Expense(person, amount, category, date, comment));
        System.out.println("Expense added successfully!");
    }

    // Check if the person exists in the list of users
    private static boolean userExists(String person) {
        for (User user : users) {
            // Check if the username matches the given username
            if (user.username.equals(person)) {
                return false;
            }
        }
        return true;
    }

    // Export the expenses to a CSV file (e.g., expenses.csv)
    private static void exportToCSV() {
        System.out.print("Enter the file name to export to (e.g., expenses.csv): ");
        String fileName = userInput.nextLine();

        // Add code for exporting the expenses to a CSV file
        try (FileWriter csvWriter = new FileWriter(fileName)) {
            csvWriter.append("Person,Amount,Category,Date,Comment\n");
            // For each expense, write the expense details to the CSV file

            // For each expense, write the expense details to the CSV file
            for (Expense expense : expenses) {
                csvWriter.append(expense.person)
                        .append(",")
                        .append(String.valueOf(expense.amount))
                        .append(",")
                        .append(expense.category)
                        .append(",")
                        .append(dateFormat.format(expense.date))
                        .append(",")
                        .append(expense.comment)
                        .append("\n");
            } // For each expense, write the expense details to the CSV file

            System.out.println("Export to CSV successful!");
        } catch (IOException e) {
            System.out.println("Error exporting to CSV: " + e.getMessage());
        }
    }

    // Filter the expenses by person and display the filtered expenses
    private static void filterExpenses() {
        System.out.print("\nEnter the person's name to filter expenses: ");
        String person = userInput.nextLine();

        // Check if the person exists in the list of users
        if (userExists(person)) {
            System.out.println("Invalid person. Please enter a valid name.");
            return;
        }

        System.out.println("\nFiltered Expenses for " + person + ":");

        // For each expense, check if the person matches the given person, and display the expense details
        for (Expense expense : expenses) {
            if (expense.person.equals(person)) {
                System.out.println("Amount: " + expense.amount +
                        ", Category: " + expense.category +
                        ", Date: " + dateFormat.format(expense.date) +
                        ", Comment: " + expense.comment);
            }
        }
    }

    // Equally split the expenses among the users and add the expenses to the list of expenses
    private static void equallySplitExpenses() {
        System.out.print("\nEnter the common expense amount to be equally split: ");
        double commonExpense = userInput.nextDouble();
        userInput.nextLine(); // Consume the newline character

        int numPeople = users.size(); // Get the number of users

        for (User user : users) {
            double equalShare = commonExpense / numPeople; // Calculate the equal share for each user
            expenses.add(new Expense(user.username, equalShare, "Equal Split", new Date(), "Equally split expense"));
        }

        System.out.println("Expenses equally split among participants!");
    }

    // Edit or delete an expense from the list of expenses
    private static void editOrDeleteExpense() {
        System.out.print("\\nEnter the index of the expense to edit or delete: ");

        // Check if the input is an integer
        if (userInput.hasNextInt()) {
            int index = userInput.nextInt();
            userInput.nextLine(); // Consume the newline character

            // Check if the index is valid (i.e., within the range of the list of expenses)
            if (index >= 0 && index < expenses.size()) {
                Expense expense = expenses.get(index);

                System.out.print("Do you want to edit (E) or delete (D) the expense? ");
                char choice = userInput.next().charAt(0);
                userInput.nextLine(); // Consume the newline character

                switch (choice) {
                    case 'E':
                    case 'e':
                        editExpense(expense); // Edit the expense
                        break;
                    case 'D':
                    case 'd':
                        expenses.remove(index); // Delete the expense
                        System.out.println("Expense deleted successfully!");
                        break;
                    default:
                        System.out.println("Invalid choice. No changes made.");
                        break;
                }
            } else {
                System.out.println("Invalid index. No changes made.");
            }
        } else {
            System.out.println("Invalid input. Please enter an integer.");
            userInput.nextLine(); // Consume the invalid token
        }
    }

    // Edit the expense details for the given expense
    private static void editExpense(Expense expense) {
        System.out.print("Enter the new expense amount: ");
        double newAmount = userInput.nextDouble();
        userInput.nextLine(); // Consume the newline character

        System.out.print("Enter the new expense category: ");
        String newCategory = userInput.nextLine();

        System.out.print("Enter the new date of the expense (YYYY-MM-DD): ");
        String newDateString = userInput.nextLine();
        Date newDate = parseDate(newDateString);

        System.out.print("Enter a new comment for the expense: ");
        String newComment = userInput.nextLine();

        expense.amount = newAmount; // Update the expense details
        expense.category = newCategory; // Update the expense details
        expense.date = newDate; // Update the expense details
        expense.comment = newComment; // Update the expense details

        System.out.println("Expense edited successfully!");
    }

    private static void expenseSummaryGraphs() {
        System.out.println("Graphical representation feature coming soon!");

        /*private static void expenseSummaryGraphs() {
        System.out.println("Generating Expense Summary Graphs...");

        Map<String, Double> categoryAmounts = new HashMap<>();

        for (Expense expense : expenses) {
            categoryAmounts.put(expense.category, categoryAmounts.getOrDefault(expense.category, 0.0) + expense.amount);
        }

        DefaultPieDataset dataset = new DefaultPieDataset();

        for (Map.Entry<String, Double> entry : categoryAmounts.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        JFreeChart pieChart = ChartFactory.createPieChart(
                "Expense Summary",
                dataset,
                true,
                true,
                false
        );

        // Display the chart in a Swing frame
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Expense Summary Pie Chart");
            frame.setLayout(new BorderLayout());
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            ChartPanel chartPanel = new ChartPanel(pieChart);
            chartPanel.setPreferredSize(new Dimension(500, 500));

            frame.add(chartPanel, BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }*/
    }

    private static Date parseDate(String dateString) {
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Error parsing date. Using current date.");
            return new Date();
        }
    }
}