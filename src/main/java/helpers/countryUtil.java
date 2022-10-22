package helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class countryUtil {
    public static ObservableList<Country> getCountries() throws SQLException {
        String getCountries = "SELECT * FROM countries";
        PreparedStatement ps = JDBC.connection.prepareCall((getCountries));
        ResultSet rs = ps.executeQuery();
        ObservableList<Country> countryList = FXCollections.observableArrayList();

        while (rs.next()) {
            int id = rs.getInt("Country_ID");
            String name = rs.getString("Country");
            Country country = new Country(id, name);
            countryList.add(country);
        }
        return countryList;
    }

    public static int getCountryId(String country) throws SQLException {
        String sql = "SELECT Country_ID FROM countries WHERE Country = ?";
        PreparedStatement ps = JDBC.connection.prepareCall((sql));
        ps.setString(1, country);
        ResultSet rs = ps.executeQuery();
        int countryId = 0;
        while (rs.next()) {
            countryId = rs.getInt("Country_ID");
        }
        return countryId;
    }
}
