package projects;
import java.util.*;
import java.sql.*;

public class createTable {
    String tableName = null;
    Scanner sc = new Scanner(System.in);

    public createTable() {
        System.out.print("Enter Table Name: ");
        tableName = sc.nextLine().trim();

        if (!Connect.databaseExists(createDatabase.database)) {
            System.out.println("Database does not exist. Please create it first.");
            return;
        }

        if (Connect.tableExists(tableName)) {
            System.out.println("Table already exists!");
            return;
        }

        System.out.print("How many columns you want in table: ");
        int numberOfCol = sc.nextInt();
        sc.nextLine();

        StringBuilder columns = new StringBuilder();
        for (int i = 1; i <= numberOfCol; i++) {
            System.out.print("Column " + i + " name: ");
            String colName = sc.nextLine();

            System.out.print("Data type (e.g., INT, VARCHAR(255)): ");
            String dataType = sc.nextLine();

            columns.append(colName).append(" ").append(dataType);
            if (i != numberOfCol) columns.append(", ");
        }
        try {
            Connection conn = Connect.getConnection(createDatabase.database);
            String sql = String.format("CREATE TABLE %s (%s)",
                    tableName, columns.toString());
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("TABLE CREATED!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean cont = true;
        while (cont) {
            System.out.println("\nSelect Operation: ");
            System.out.println("1. Insert data\n2. Delete data\n3. Update data\n4. Read data\n5. Exit");
            System.out.print("Enter: ");
            int operation = sc.nextInt();
            switch (operation) {
                case 1:
                    Dao.insert(tableName);
                    break;
                case 2:
                    System.out.print("Enter the column name to identify record: ");
                    String colName = sc.next();
                    sc.nextLine();
                    System.out.print("\nEnter the column value to identify record: ");
                    String colVal = sc.next();
                    Dao.delete(tableName,colName,colVal);
                    break;
                case 3:
                    Dao.update(tableName);
                    break;
                case 4:
                    Dao.read(tableName);
                    break;
                case 5:
                    cont = false;
                    break;
                default:
                    System.out.println("Kindly enter valid choice");
            }
        }
    }
}
