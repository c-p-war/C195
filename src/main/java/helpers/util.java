package helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.User;

import java.lang.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Holds utility methods for date/times, errors, and alerts
 */
public class util {
    private static ZoneId sysZoneId = ZoneId.systemDefault();
    private static ZoneId utcZoneId = ZoneId.of("UTC");
    private static DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static ResourceBundle rb = ResourceBundle.getBundle("bundle/lang", Locale.getDefault());

    /**
     * Checks the supplied user credentials for DB records
     *
     * @param userName
     * @param in_password
     * @return List of users with matching credentials
     * @throws SQLException
     */
    public static ObservableList<User> checkUsers(String userName, String in_password) throws SQLException {
        String sql = "SELECT * FROM users WHERE User_Name = ? AND Password =?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, in_password);
        ResultSet rs = ps.executeQuery();
        ObservableList<User> userList = FXCollections.observableArrayList();
        while (rs.next()) {
            int id = rs.getInt("User_ID");
            String name = rs.getString("User_Name");
            String password = rs.getString("Password");
            User user = new User(id, name, password);
            userList.add(user);
        }
        return userList;
    }

    /**
     * @param string
     * @return An error message populated with the passed string
     */
    public static Alert stringToError(String string) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (Locale.getDefault().getLanguage().equals("fr")) {
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("errorMismatch"));
            alert.showAndWait();
        } else {
            alert.setTitle("Error");
            alert.setContentText(string);
            alert.showAndWait();
        }
        return alert;
    }

    /**
     * @param string
     * @return An alert message populated with the passed string
     */
    public static Alert stringToAlert(String string) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (Locale.getDefault().getLanguage().equals("fr")) {
            alert.setTitle(rb.getString("alert"));
            alert.setContentText(string);
            alert.showAndWait();
        } else {
            alert.setTitle("Alert");
            alert.setContentText(string);
            alert.showAndWait();
        }
        return alert;
    }

    /**
     * Converts String to LocalDateTime
     *
     * @param dateTime
     * @return LocalDateTime object
     */
    public static LocalDateTime getLdtFromString(String dateTime) {
        LocalDateTime converted = LocalDateTime.parse(dateTime, dt);
        return converted;
    }

    /**
     * Converts input to EST
     *
     * @param ldt
     * @return EST based LocalDateTime object
     */
    public static LocalDateTime localToEST(LocalDateTime ldt) {
        ZoneId estZoneId = ZoneId.of("America/New_York");
        ZonedDateTime sysZDT = ZonedDateTime.of(ldt, sysZoneId);
        ZonedDateTime estZDT = ZonedDateTime.ofInstant(sysZDT.toInstant(), estZoneId);
        return getLdtFromString(estZDT.format(dt));
    }

    /**
     * Converts input to UTC
     *
     * @param ldt
     * @return UTC based ZonedDateTime object
     */
    public static String localToUTC(LocalDateTime ldt) {
        ZonedDateTime sysZDT = ZonedDateTime.of(ldt, sysZoneId);
        ZonedDateTime utcZDT = ZonedDateTime.ofInstant(sysZDT.toInstant(), utcZoneId);
        return utcZDT.format(dt);
    }

    /**
     * Converts utc string to LocalDateTime
     *
     * @param ldt
     * @return System based LocalDateTime object
     */
    public static String utcToLocal(LocalDateTime ldt) {
        ZonedDateTime utcZDT = ZonedDateTime.of(ldt, utcZoneId);
        ZonedDateTime sysZDT = ZonedDateTime.ofInstant(utcZDT.toInstant(), sysZoneId);
        return sysZDT.format(dt);
    }
}
