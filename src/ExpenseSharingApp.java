import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ExpenseSharingApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<User> users = new ArrayList<>();
    private static final List<Expense> expenses = new ArrayList<>();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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
                    addExpense(null);
                    break;
                case 4:
                    // Add export feature
                    exportExpenses();
                    break;
                case 5:
                    // Add filtering feature
                    expenseFiltering();
                    break;
                case 6:
                    // Add equally split feature
                    equallySplitExpenses();
                    break;
                case 7:
                    // Add edit or delete feature
                    editOrDeleteExpense(null);
                    break;
                case 8:
                    // Add graphical representation feature
                    expenseSummaryGraphs();
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

    private static void addExpense(User currentUser) {
        System.out.print("\nEnter the name of the person incurring the expense: ");
        String person = scanner.nextLine();

        if (!people.contains(person)) {
            System.out.println("Invalid person. Please enter a valid name.");
            return;
        }

        System.out.print("Enter the expense amount for " + person + ": ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter the expense category: ");
        String category = scanner.nextLine();

        System.out.print("Enter the date of the expense (YYYY-MM-DD): ");
        String dateString = scanner.nextLine();
        Date date = parseDate(dateString);

        System.out.print("Enter a comment for the expense: ");
        String comment = scanner.nextLine();

        expenses.add(new Expense(person, amount, category, date, comment));
        System.out.println("Expense added successfully!");
    }

    private static void exportToCSV() {
        System.out.print("Enter the file name to export to (e.g., expenses.csv): ");
        String fileName = scanner.nextLine();

        try (FileWriter csvWriter = new FileWriter(fileName)) {
            csvWriter.append("Person,Amount,Category,Date,Comment\n");

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
            }

            System.out.println("Export to CSV successful!");
        } catch (IOException e) {
            System.out.println("Error exporting to CSV: " + e.getMessage());
        }
    }

    private static void filterExpenses() {
        System.out.print("\nEnter the person's name to filter expenses: ");
        String person = scanner.nextLine();

        if (!people.contains(person)) {
            System.out.println("Invalid person. Please enter a valid name.");
            return;
        }

        System.out.println("\nFiltered Expenses for " + person + ":");

        for (Expense expense : expenses) {
            if (expense.person.equals(person)) {
                System.out.println("Amount: " + expense.amount +
                        ", Category: " + expense.category +
                        ", Date: " + dateFormat.format(expense.date) +
                        ", Comment: " + expense.comment);
            }
        }
    }

    private static void equallySplitExpenses() {
        System.out.print("\nEnter the common expense amount to be equally split: ");
        double commonExpense = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        int numPeople = people.size();

        for (String person : people) {
            double equalShare = commonExpense / numPeople;
            expenses.add(new Expense(person, equalShare, "Equal Split", new Date(), "Equally split expense"));
        }

        System.out.println("Expenses equally split among participants!");
    }

    private static void editOrDeleteExpense(User currentUser) {
        System.out.print("\nEnter the index of the expense to edit or delete: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (index >= 0 && index < expenses.size()) {
            Expense expense = expenses.get(index);

            if (expense.person.equals(currentUser.username)) {
                System.out.print("Do you want to edit (E) or delete (D) the expense? ");
                char choice = scanner.next().charAt(0);
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 'E':
                    case 'e':
                        editExpense(expense);
                        break;
                    case 'D':
                    case 'd':
                        expenses.remove(index);
                        System.out.println("Expense deleted successfully!");
                        break;
                    default:
                        System.out.println("Invalid choice. No changes made.");
                        break;
                }
            } else {
                System.out.println("You can only edit or delete your own expenses.");
            }
        } else {
            System.out.println("Invalid index. No changes made.");
        }
    }

    private static void editExpense(Expense expense) {
        System.out.print("Enter the new expense amount: ");
        double newAmount = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter the new expense category: ");
        String newCategory = scanner.nextLine();

        System.out.print("Enter the new date of the expense (YYYY-MM-DD): ");
        String newDateString = scanner.nextLine();
        Date newDate = parseDate(newDateString);

        System.out.print("Enter a new comment for the expense: ");
        String newComment = scanner.nextLine();

        expense.amount = newAmount;
        expense.category = newCategory;
        expense.date = newDate;
        expense.comment = newComment;

        System.out.println("Expense edited successfully!");
    }

    private static void expenseSummaryGraphs() {
        // Add code for generating graphs (e.g., pie charts, bar graphs) based on the expenses
        // You may use external libraries like JFreeChart for graphical representations
        System.out.println("Graphical representation feature coming soon!");
    }
}