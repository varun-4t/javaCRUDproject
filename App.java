package projects;

import java.sql.SQLException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("\n\t\tWelcome to the Data Management System\n");
            boolean cont = true;
            int choice = -1;
            String db;
            while(cont){
                System.out.println("What do you want to do?\n1. Create a New Database\n2. Work with Existing Database\n3. Exit");
                System.out.print("SELECT: ");
                choice = sc.nextInt();
                switch(choice){
                    case 1:
                        new createDatabase();
                        break;
                    case 2:
                        System.out.print("\nEnter Name of Database: ");
                        db = sc.next();
                        new createDatabase(db);
                        break;
                    case 3:
                        cont = false;
                        break;
                    default:
                        System.out.println("Enter a valid choice");
                }
    }
}catch(Exception e){
        e.printStackTrace();
        }
    }
}
