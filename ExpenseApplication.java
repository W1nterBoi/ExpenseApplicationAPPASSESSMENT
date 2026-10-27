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
                switch(choice){
                    case 1:
                        addExpense();
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        cont = false;
                        break;
                    
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Error");
        }

    }

    static void createTable() throws SQLException{
        String sql = "CREATE TABLE IF NOT EXISTS expenses (id INT AUTO_INCREMENT PRIMARY KEY, expense_dare DATE, category VARCHAR(50), description VARCHAR(200), amount DECIMAL(10, 2))";
        Statement statement = connect.createStatement();
        statement.executeUpdate(sql);
        statement.close();
    }
    static void addExpense() throw SQLException{
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
        statement.setString(3, descroiption);
        statement.setDouble(4, amount);
        

    }


}


