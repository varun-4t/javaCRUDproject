package projects;

public class Query {
    static String createDatabase = "CREATE DATABASE IF NOT EXISTS ";

    static String delete = "DELETE FROM %s WHERE %s = ?";

    static String update = "UPDATE %s SET %s = ? WHERE %s = ?";

    static String read = "SELECT * FROM %s";

    static String readCondition = "SELECT * FROM %s WHERE %s = ?";

}
