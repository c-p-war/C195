package helpers;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Handles connections to the DB
 */
public abstract class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    // + "?connectionTimezone = SERVER";
    private static final String jdbcURL = protocol + vendor + location + databaseName;
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "";
    private static final String password = "";
    public static Connection connection;

    public static void openConnection(){
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcURL, userName, password);
            System.out.println("Connection Successful!");
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void closeConnection(){
        try {
            System.out.println("Connection Closed!");
            connection.close();
        }catch (Exception e){
            System.out.println("Error:" + e.getMessage());
        }
    }

}
