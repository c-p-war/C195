package helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.Division;

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
        System.out.println(countryList);
        return countryList;
    }
}
