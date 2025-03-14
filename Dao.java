package projects;

import java.sql.*;
import java.util.*;

public class Dao {
    static Scanner sc = new Scanner(System.in);
    public static void insert(String tableName){
    try {
        Scanner sc = new Scanner(System.in);
        Connection conn = Connect.getConnection(createDatabase.database);
        // Get column metadata
        try (Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM " + tableName + " LIMIT 0")) {

            ResultSetMetaData rsMetaData = rs.getMetaData();
            int columnCount = rsMetaData.getColumnCount();

            // Build placeholders (?, ?, ?)
            StringBuilder placeholders = new StringBuilder();
            for (int i = 1; i <= columnCount; i++) {
                placeholders.append("?");
                if (i < columnCount) placeholders.append(", ");
            }

            // Correct SQL: Insert table name directly (validate it first!)
            String sql = "INSERT INTO " + tableName + " VALUES (" + placeholders + ")";

            try (PreparedStatement pstm = conn.prepareStatement(sql)) {
                // Collect values
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print("Enter value for " + rsMetaData.getColumnName(i) + ": ");
                    String value = sc.nextLine();
                    pstm.setString(i, value); // For simplicity; handle data types properly in real code
                }

                pstm.executeUpdate();
                System.out.println("\nValues inserted!");
            }
        }
    }catch(Exception e){
    e.printStackTrace();
    }
    }

    public static void delete(String tableName, String colName, String colValue){
        try {
            Connection conn = Connect.getConnection(createDatabase.database);

            String sql = String.format(Query.delete, tableName, colName);
            assert conn != null;
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, colValue);
            pstm.executeUpdate();
            System.out.println("\nDeletion Completed Successfully");

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void update(String tableName){
        try{
            Connection conn = Connect.getConnection(createDatabase.database);
            sc.nextLine();
            System.out.print("\nEnter the name of column you want to update: ");
            String colName = sc.nextLine();
            System.out.print("\nEnter the value of column you want to update: ");
            String colValue = sc.nextLine();
            System.out.print("\nEnter the name of column to identify record: ");
            String colName2 = sc.nextLine();
            System.out.print("\nEnter the value of column to identify record: ");
            String colValue2 = sc.nextLine();

            String sql = String.format(Query.update, tableName, colName, colName2 );
            PreparedStatement pstm = conn.prepareStatement(sql);

            pstm.setString(1,colValue);
            pstm.setString(2,colValue2);

            pstm.executeUpdate();
            System.out.println("\nUpdated Successfully");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void read(String tableName){
        Connection conn = Connect.getConnection(createDatabase.database);
        int choice;
        System.out.println("\nSELECT:\n1. Read Entire database\n2. Read with specific condition");
        System.out.print("Enter Choice: ");
        choice = sc.nextInt();
        try {
            switch (choice) {
                case 1:
                    try {
                        String sql = String.format(Query.read, tableName);
                        assert conn != null;
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(sql);

                            ResultSetMetaData meta = rs.getMetaData();
                            int colCount = meta.getColumnCount();

                            // Print header
                            for (int i = 1; i <= colCount; i++) {
                                System.out.print(meta.getColumnName(i) + "\t");
                            }
                            System.out.println();

                            // Print data
                            while (rs.next()) {
                                for (int i = 1; i <= colCount; i++) {
                                    System.out.print(rs.getString(i) + "\t");
                                }
                                System.out.println();
                            }
                            return;
                        }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    System.out.print("Select the name of column on the basis of which you want to read: ");
                    String colName = sc.next();
                    System.out.print("\nEnter the value of column: ");
                    String colValue = sc.nextLine();
                    try{
                        String sql = String.format(Query.read, tableName,colName);
                        PreparedStatement pstm = conn.prepareStatement(sql);
                        pstm.setString(1,colValue);
                        pstm.executeUpdate();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    return;
                default:
                    System.out.println("Enter Valid Choice");
            }
            }catch(Exception e){
            e.printStackTrace();
        }
    }
}