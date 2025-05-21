package stepDefinitions;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection;

    static String URL = "jdbc:mysql://localhost:3306/user_management?useSSL=false&allowPublicKeyRetrieval=true";
    static String USER = "krish_app_user";
    static String PASSWORD = "Krish@123"; // Replace with your MySQL password


    // Get or establish connection
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Load the JDBC driver (optional for newer JDBC versions)
                Class.forName("com.mysql.cj.jdbc.Driver"); // Use "org.postgresql.Driver" for PostgreSQL
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connection established.");
            } catch (ClassNotFoundException e) {
                throw new SQLException("JDBC Driver not found", e);
            }
        }
        return connection;
    }

    // Close connection
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


