package helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.ReportCountry;

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

    public static ObservableList<ReportCountry> getCustByCountry() throws SQLException {
        String getCountries =
                "SELECT \n" +
                "c.Country,\n" +
                "count(Customer_ID) AS 'count'\n" +
                "FROM countries AS c \n" +
                "JOIN first_level_divisions AS d\n" +
                "ON d.Country_ID = c.Country_ID\n" +
                "JOIN customers AS cu\n" +
                "ON cu.Division_ID = d.Division_ID\n" +
                "GROUP BY c.Country";
        PreparedStatement ps = JDBC.connection.prepareCall((getCountries));
        ResultSet rs = ps.executeQuery();
        ObservableList<ReportCountry> countryList = FXCollections.observableArrayList();
        while (rs.next()) {
            String country = rs.getString("Country");
            int count = rs.getInt("count");
            ReportCountry report = new ReportCountry(country, count);
            countryList.add(report);
        }
        return countryList;
    }
}
