package projects;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.sql.Connection;

public class createDatabase {
    Scanner sc = new Scanner(System.in);
    static String database;

    public createDatabase(){
        System.out.print("Enter Database Name: ");
        database = sc.nextLine().trim();

        try{
            //establish the connection
            Connection conn = Connect.getConnection(null); //because database is not created yet
            // In createDatabase.java constructor (replace PreparedStatement)
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(Query.createDatabase + database);
            System.out.println("\nDataBase Created\n");

            // Switch to new database
            conn = Connect.getConnection(database);

            boolean cont = true;
            while(cont) {
                //asking to create a table
                System.out.println("\n1. Do you want to create a new table?\n2. Exit");
                System.out.print("Enter choice: ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        new createTable();
                        break;
                    case 2:
                        cont = false;
                        break;
                    default:
                        System.out.println("Kindly enter valid choice");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public createDatabase(String db){

        // Check if database exists
        if (!Connect.databaseExists(db)) {
            System.out.println("Error: Database does not exist.\n");
            return; // Exit constructor
        }

        database = db;


        boolean cont = true;
        while(cont) {
            //asking to create a table
            System.out.println("1. Do you want to create a new table?\n2. Work with existing table\n3. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    new createTable();
                    break;
                case 2:
                    System.out.print("Enter Table name: ");
                    String tableName = sc.next();

                    boolean contInner = true;
                    while (contInner) {
                        System.out.println("\nSelect Operation: ");
                        System.out.println("\n1. Insert data\n2. Delete data\n3. Update data\n4. Read data\n5. Exit");
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
                                Dao.delete(tableName, colName, colVal);
                                break;
                            case 3:
                                Dao.update(tableName);
                                break;
                            case 4:
                                Dao.read(tableName);
                                break;
                            case 5:
                                contInner = false;
                                break;
                            default:
                                System.out.println("Kindly enter valid choice");
                        }
                    }
                    break;
                case 3:
                    cont = false;
                    break;
                default:
                    System.out.println("Kindly enter valid choice");
            }
        }
    }

}