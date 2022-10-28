package helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Utility methods for the Customer object. Holds SQL statements.
 */
public class customerUtil {
    /**
     * @param divisionId
     * @return Division name based on the given divisionId
     * @throws SQLException
     */
    public static String getDivisionName(int divisionId) throws SQLException {
        String sql = "SELECT Division FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareCall((sql));
        ps.setInt(1, divisionId);
        ResultSet rs = ps.executeQuery();
        String divisionName = null;
        while (rs.next()) {
            divisionName = rs.getString("Division");
        }
        return divisionName;
    }

    /**
     * @param divisionName
     * @return Division ID based on the given divisionName
     * @throws SQLException
     */
    public static int getDivisionId(String divisionName) throws SQLException {
        String sql = "SELECT Division_ID FROM first_level_divisions WHERE Division = ?";
        PreparedStatement ps = JDBC.connection.prepareCall((sql));
        ps.setString(1, divisionName);
        ResultSet rs = ps.executeQuery();
        int divisionId = 0;
        while (rs.next()) {
            divisionId = rs.getInt("Division_ID");
        }
        return divisionId;
    }

    /**
     * @param divisionId
     * @return Country name for a given division id
     * @throws SQLException
     */
    public static String getCountryName(int divisionId) throws SQLException {
        String sql = "SELECT Country FROM countries AS c JOIN first_level_divisions AS s ON s.Country_ID = c.Country_ID  WHERE s.Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareCall((sql));
        ps.setInt(1, divisionId);
        ResultSet rs = ps.executeQuery();
        String countryName = null;
        while (rs.next()) {
            countryName = rs.getString("Country");
        }
        return countryName;
    }

    /**
     * @param divisionId
     * @return Country ID for a given division ID
     * @throws SQLException
     */
    public static int getCountryId(int divisionId) throws SQLException {
        String sql = "SELECT Country_ID FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareCall((sql));
        ps.setInt(1, divisionId);
        ResultSet rs = ps.executeQuery();
        int countryId = 0;
        while (rs.next()) {
            countryId = rs.getInt("Country_ID");
        }
        return countryId;
    }

    /**
     * @return All customers from the DB
     * @throws SQLException
     */
    public static ObservableList<Customer> getCustomers() throws SQLException {
        String getCustomer = " SELECT * FROM customers";
        PreparedStatement ps = JDBC.connection.prepareCall(getCustomer);
        ResultSet rs = ps.executeQuery();
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        while (rs.next()) {
            int id = rs.getInt("Customer_ID");
            String name = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postal = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            String division = customerUtil.getDivisionName(rs.getInt("Division_ID"));
            String country = customerUtil.getCountryName(rs.getInt("Division_ID"));
            Customer customer = new Customer(id, name, address, postal, phone, division, country);
            customerList.add(customer);
        }
        return customerList;
    }

    /**
     * Checks DB for customer by customerId
     * @param in_customerId
     * @return Returns true if found, false if not.
     * @throws SQLException
     */
    public static boolean checkCustomerExists(int in_customerId) throws SQLException {
        String getCustomer = " SELECT * FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareCall(getCustomer);
        ps.setInt(1, in_customerId);
        ResultSet rs = ps.executeQuery();
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        while (rs.next()) {
            int id = rs.getInt("Customer_ID");
            String name = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postal = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            String division = customerUtil.getDivisionName(rs.getInt("Division_ID"));
            String country = customerUtil.getCountryName(rs.getInt("Division_ID"));
            Customer customer = new Customer(id, name, address, postal, phone, division, country);
            customerList.add(customer);
        }
        if (customerList.size() == 1){
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param in_customerId
     * @return Appointments from the database that have the provided customerId
     * @throws SQLException
     */
    public static ObservableList<Appointment> getCustomerAppointments(int in_customerId) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE Customer_Id = ?";
        PreparedStatement ps = JDBC.connection.prepareCall(sql);
        ps.setInt(1, in_customerId);
        ResultSet rs = ps.executeQuery();
        ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();

        while (rs.next()) {
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            String start = util.utcToLocal(rs.getTimestamp("Start").toLocalDateTime());
            String end = util.utcToLocal(rs.getTimestamp("End").toLocalDateTime());
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            String contactName = contactUtil.getContactName(rs.getInt("Contact_ID"));
            Appointment appointment = new Appointment(id, title, description, location, type, start, end, customerId, userId, contactName);
            appointmentsList.add(appointment);
        }
        System.out.println(appointmentsList);
        return appointmentsList;
    }

    /**
     * Adds a customer to the DB
     *
     * @param customer
     */
    public static void addCustomer(Customer customer) {
        try {
            String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customer.getId());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getPostal());
            ps.setString(5, customer.getPhone());
            ps.setInt(6, getDivisionId(customer.getDivision()));
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Updates the selected customer in the DB
     *
     * @param customer
     */
    public static void updateCustomer(Customer customer) {
        try {
            String sql = "UPDATE CUSTOMERS SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareCall(sql);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getPostal());
            ps.setString(4, customer.getPhone());
            ps.setInt(5, getDivisionId(customer.getDivision()));
            ps.setInt(6, customer.getId());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Deletes the selected customer from the DB
     *
     * @param customer
     */
    public static void deleteCustomer(Customer customer) {
        try {
            String deleteAppointments = "DELETE FROM appointments WHERE Customer_ID = ?";
            PreparedStatement delete_apt_ps = JDBC.connection.prepareCall(deleteAppointments
            );
            delete_apt_ps.setInt(1, customer.getId());
            delete_apt_ps.executeUpdate();

            String deleteCustomer = "DELETE FROM customers where Customer_ID = ?";
            PreparedStatement delete_cust_ps = JDBC.connection.prepareCall(deleteCustomer);
            delete_cust_ps.setInt(1, customer.getId());
            delete_cust_ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}
