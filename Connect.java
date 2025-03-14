package projects;

import java.sql.*;
import java.util.*;
import java.io.FileInputStream;

public class Connect {
    private static String url = "jdbc:mysql://localhost:3306/";
    private static String username;
    private static String password;

    static Connection conn = null;

    static {
        try {
            Properties props = new Properties();
            FileInputStream fis = new FileInputStream("config.properties");
            props.load(fis);

            username = props.getProperty("DB_USER");
            password = props.getProperty("DB_PASS");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(String db) {
        try {
            String fullUrl = url + (db != null ? db : "");
            return DriverManager.getConnection(fullUrl, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean databaseExists(String dbName) {
        try (Connection conn = getConnection(null);
             PreparedStatement stmt = conn.prepareStatement("SHOW DATABASES LIKE ?")) {
            stmt.setString(1, dbName);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // If a result is found, the database exists
            }
        } catch (Exception e) {
            System.err.println("Error: Unable to verify database existence.");
            e.printStackTrace();
        }
        return false;
    }
    public static boolean tableExists(String tableName, String database) {
        try (Connection conn = getConnection(database);
             PreparedStatement stmt = conn.prepareStatement("SHOW TABLES LIKE ?")) {
            stmt.setString(1, tableName);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Table exists
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
