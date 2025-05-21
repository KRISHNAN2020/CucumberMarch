package stepDefinitions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserTableExample {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/user_management?useSSL=false&allowPublicKeyRetrieval=true";
        String user = "krish_app_user";
        String password = "Krish@123"; // Replace with your MySQL password

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("first_name") + " " + rs.getString("last_name") +
                        ", Email: " + rs.getString("email") +
                        ", Created: " + rs.getTimestamp("created_at"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}