import java.sql.*;
import java.util.Scanner;

public class ExpenseApplication {
    static String url = "jdbc:mysql://localhost:3306/testdb";
    static String user = "root";
    static String psswd = "strong";
    
    static Scanner get = new Scanner(System.in);
    static Connection connect;

    public static void main(String[] args){
        try {
            connect= DriverManager.getConnection(url, user, psswd);
            createTable();
            int choice;
            boolean cont = true;
            while(cont){
                System.out.println("\n\n---EXPENSE TRACKER---\n1. Add Expense \n2. View Month Expense \n3. Month Total by Category \n4. Update Expense \n5. Delete Expense \n6. Exit");
                choice = get.nextInt();
                switch(choice){
                    case 1:
                        addExpense();
                        break;
                    case 2:
                        viewByMonth();
                        break;
                    case 3:
                        totalsByCategory();
                        break;
                    case 4:
                        updateExpenses();
                        break;
                    case 5:
                        deleteExpenses();
                        break;
                    case 6:
                        cont = false;
                        break;
                    
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }

    }

    static void createTable() throws SQLException{
        String sql = "CREATE TABLE IF NOT EXISTS expenses (id INT AUTO_INCREMENT PRIMARY KEY, expense_dare DATE, category VARCHAR(50), description VARCHAR(200), amount DECIMAL(10, 2))";
        Statement statement = connect.createStatement();
        statement.executeUpdate(sql);
        statement.close();
    }
    static void addExpense() throws SQLException{
        System.out.println("Enter Date        : ");
        Date date = Date.valueOf(get.nextLine());
        System.out.println("Enter Category    : ");
        String category = get.nextLine();
        System.out.println("Enter Description : ");
        String description = get.nextLine();
        System.out.println("Enter Amount      : ");
        double amount = get.nextDouble();

        String sql = "INSERT INTO expenses (expense_date, category, description, amount) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connect.prepareStatement(sql);
        statement.setDate(1, date);
        statement.setString(2, category);
        statement.setString(3, description);
        statement.setDouble(4, amount);
        statement.execute();
        statement.executeUpdate();
        statement.close();
    }
    static void viewByMonth() throws SQLException{
        System.out.println("Year  : " );
        int year = get.nextInt();
        System.out.println("Month : " );
        int month = get.nextInt();

        String sql = "SELECT * FROM expenses WHERE YEAR(expense_date) = ? AND MONTH(expense_date) = ? ORDER BY expense_date";
        PreparedStatement statement = connect.prepareStatement(sql);
        statement.setInt(1, year);
        statement.setInt(2, month);
        ResultSet results = statement.executeQuery();
        System.out.printf("%-5s %-12s %-15s %-25s %10s%n", "ID", "Date", "Category", "Description", "Amount");
    
        double total = 0;
        while(results.next()){
            System.out.printf("%-5d %-12s %-15s %-25s %10.2f%n", results.getInt("id"), results.getDate("expense_date"), results.getString("category"), results.getString("description"), results.getDouble("amount"));
            total = total + results.getDouble("amount");
        }
        System.out.printf("Month total: %.2f%n", total);
        results.close();
        statement.close();
    }
    static void totalsByCategory() throws SQLException{
        System.out.println("Year  : " );
        int year = get.nextInt();
        System.out.println("Month : " );
        int month = get.nextInt();
 
        String sql = "SELECT category, SUM(amount) AS total FROM expenses WHERE YEAR(expense_date) = ? AND MONTH(expense_date) = ? GROUP BY category";
        PreparedStatement statement = connect.prepareStatement(sql);
        statement.setInt(1, year);
        statement.setInt(2, month);
        ResultSet results = statement.executeQuery();
 
        System.out.printf("%-15s %10s%n", "Category", "Total");
        while (results.next()) {
            System.out.printf("%-15s %10.2f%n", results.getString("category"), results.getDouble("total"));
        }
        results.close();
        statement.close();
    }
    static void updateExpenses() throws SQLException{
        System.out.print("ID of expense   : ");
        int id = get.nextInt();
        System.out.print("New date        : ");
        Date date = Date.valueOf(get.nextLine());
        System.out.print("New category    : ");
        String category = get.nextLine();
        System.out.print("New description : ");
        String description = get.nextLine();
        System.out.print("New amount      : ");
        double amount = Double.parseDouble(get.nextLine());
 
        String sql = "UPDATE expenses SET expense_date = ?, category = ?, description = ?, amount = ? WHERE id = ?";
        PreparedStatement statement = connect.prepareStatement(sql);
        statement.setDate(1, date);
        statement.setString(2, category);
        statement.setString(3, description);
        statement.setDouble(4, amount);
        statement.setInt(5, id);
        statement.executeUpdate();
        statement.close();
        System.out.println("Expense updated.");
    }
    static void deleteExpenses() throws SQLException{
        System.out.print("ID of expense: ");
        int id = get.nextInt();
 
        PreparedStatement statement = connect.prepareStatement("DELETE FROM expenses WHERE id = ?");
        statement.setInt(1, id);
        statement.executeUpdate();
        statement.close();
        System.out.println("Expense deleted.");

    }
}


