package helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.User;

import java.lang.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class util {
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
        System.out.println(userList.size());
        System.out.println(userList);
        return userList;
    }

    public static Alert stringToError(String string) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(string);
        alert.showAndWait();
        return alert;
    }

    public static Alert stringToAlert(String string) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setContentText(string);
        alert.showAndWait();
        return alert;
    }

    public static java.sql.Timestamp getTimeStamp() {
        ZoneId zoneid = ZoneId.of("UTC");
        LocalDateTime localDateTime = LocalDateTime.now(zoneid);
        java.sql.Timestamp timeStamp = Timestamp.valueOf(localDateTime);
        return timeStamp;
    }

    public static java.sql.Date getDate() {
        java.sql.Date date = java.sql.Date.valueOf(LocalDate.now());
        return date;
    }


}
