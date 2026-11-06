package reference;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlainApp_Original {
    // Replace with your actual database details
    private static final String URL = "jdbc:mysql://localhost:3306/plain_app_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Nvphari@123";

    public static void main(String[] args) {
        // 1. Optional for newer drivers, but good practice: Load the driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
            return;
        }

        // 2. Establish connection and perform operations
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Connected to database successfully!");

            // --- INSERT EXAMPLE ---
            String insertSql = "INSERT INTO users (name) VALUES (?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setString(1, "Test User");
                int rows = pstmt.executeUpdate();
                System.out.println(rows + " row(s) inserted.");
            }

            // --- SELECT EXAMPLE ---
            String selectSql = "SELECT id, name FROM users";
            try (PreparedStatement pstmt = conn.prepareStatement(selectSql);
                 ResultSet rs = pstmt.executeQuery()) {
                
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    System.out.println("User found: ID=" + id + ", Name=" + name);
                }
            }

        } catch (SQLException e) {
            System.err.println("Database connection or query failed.");
            e.printStackTrace();
        }
    }
}