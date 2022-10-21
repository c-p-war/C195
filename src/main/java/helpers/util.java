package helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.User;

import java.lang.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class util {
    public static  ObservableList<User> checkUsers(String userName, String in_password) throws SQLException {
        String sql ="SELECT * FROM users WHERE User_Name = ? AND Password =?";
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

    public static String contNameToId(String contactName) {
        System.out.println("Contact name: " + contactName);
        // TODO: Finish logic
        return contactName;
    }

    public static int contIdToName(int contactId) {
        System.out.println("Contact id: " + contactId);
        // TODO: Finish logic
        return contactId;
    }

    public static String userNameToId(String userName) {
        System.out.println("User name: " + userName);
        // TODO: Finish logic
        return userName;
    }

    public static int userIdToName(int userId) {
        System.out.println("User id: " + userId);
        // TODO: Finish logic
        return userId;
    }

    // TODO: This may need a different type, but we should be able to grab all divisions based on the country id in the db
    public static int getDivisionsByCountry(int countryId){
        System.out.println("Country id: " + countryId);
        return countryId;
    }

    public static int getCountryByDivision(int divisionId){
        System.out.println("Division id: " + divisionId);
        return divisionId;
    }
    // TODO: Date conversions need new types
    // TODO: Finish logic
    public static String convertLocal(String endingFormat){
        String convertedDate = null;
        if (Objects.equals(endingFormat, "UTC")) {
            System.out.println("Ending format: UTC");
            convertedDate = "Local -> UTC";
        }  if (Objects.equals(endingFormat, "System")) {
            System.out.println("Ending format: System");
            convertedDate = "Local -> System";
        }
        return convertedDate;
    }

    public static String convertUTC(String endingFormat){
        String convertedDate = null;
        if (Objects.equals(endingFormat, "System")) {
            System.out.println("Ending format: System");
            convertedDate = "UTC -> System";
        }  if (Objects.equals(endingFormat, "Local")) {
            System.out.println("Ending format: Local");
            convertedDate = "UTC -> Local";
        }
        return convertedDate;
    }

    public static String convertSystem(String endingFormat){
        String convertedDate = null;
        if (Objects.equals(endingFormat, "UTC")) {
            System.out.println("Ending format: UTC");
            convertedDate = "System -> UTC";
        }  if (Objects.equals(endingFormat, "Local")) {
            System.out.println("Ending format: Local");
            convertedDate = "System -> Local";
        }
        return convertedDate;
    }



}
