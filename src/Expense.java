import java.util.Date;

class Expense {
    String person;
    double amount;
    String category;
    Date date;
    String comment;

    public Expense(String person, double amount, String category, Date date, String comment) {
        this.person = person;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.comment = comment;
    }
}