package helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class customerUtil {
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
// TODO: Correct division id
    // TODO: Add country

//    public static ObservableList<Customer> getCustomersByDivision(int in_divisionId) throws SQLException {
//        String sql = "SELECT * FROM customers WHERE Division_ID = ?";
//        PreparedStatement ps = JDBC.connection.prepareCall(sql);
//        ps.setInt(1, in_divisionId);
//        ResultSet rs = ps.executeQuery();
//        ObservableList<Customer> customerList = FXCollections.observableArrayList();
//
//        while (rs.next()) {
//            int id = rs.getInt("Customer_ID");
//            String name = rs.getString("Customer_Name");
//            String address = rs.getString("Address");
//            String postal = rs.getString("Postal_Code");
//            String phone = rs.getString("Phone");
//            int divisionId = rs.getInt("Division_ID");
//            Customer customer = new Customer(id, name, address, postal, phone, divisionId);
//            customerList.add(customer);
//        }
//        System.out.println(customerList);
//        return customerList;
//    }

// TODO: Correct division id
    // TODO: Add country

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

    public static void updateCustomer(Customer customer) throws SQLException {
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

    public static void deleteCustomer(Customer customer) throws SQLException {
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
