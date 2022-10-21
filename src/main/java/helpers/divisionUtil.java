package helpers;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class divisionUtil {
    public static ObservableList<Division> getDivisions() throws SQLException {
        String getDivisions = "SELECT * FROM first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareCall((getDivisions));
        ResultSet rs = ps.executeQuery();
        ObservableList<Division> divisionList = FXCollections.observableArrayList();

        while (rs.next()) {
            int id = rs.getInt("Division_ID");
            String name = rs.getString("Division");
            int countryId = rs.getInt("Country_ID");
            Division division = new Division(id, name, countryId);
            divisionList.add(division);
        }
        System.out.println(divisionList);
        return divisionList;
    }

    public static ObservableList<Division> getDivByCountry(int countryId) throws SQLException {
        String getDivByCountry = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(getDivByCountry);
        ps.setInt(1, countryId);
        ResultSet rs = ps.executeQuery();
        ObservableList<Division> divisionList = FXCollections.observableArrayList();
        while (rs.next()) {
            int id = rs.getInt("Division_ID");
            String name = rs.getString("Division");
            int out_countryId = rs.getInt("Country_ID");
            Division division = new Division(id, name, out_countryId);
            divisionList.add(division);
        }
        System.out.println(divisionList);
        return divisionList;
    }
}
