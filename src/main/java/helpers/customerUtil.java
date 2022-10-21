package helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class customerUtil {
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
            int divisionId = rs.getInt("Division_ID");
            Customer customer = new Customer(id, name, address, postal, phone, divisionId);
            customerList.add(customer);
        }
        System.out.println(customerList);
        return customerList;
    }

    public static ObservableList<Customer> getCustomersByDivision(int in_divisionId) throws SQLException {
        String sql = "SELECT * FROM customers WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareCall(sql);
        ps.setInt(1, in_divisionId);
        ResultSet rs = ps.executeQuery();
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        while (rs.next()) {
            int id = rs.getInt("Customer_ID");
            String name = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postal = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            int divisionId = rs.getInt("Division_ID");
            Customer customer = new Customer(id, name, address, postal, phone, divisionId);
            customerList.add(customer);
        }
        System.out.println(customerList);
        return customerList;
    }


    public static void addCustomer(Customer customer) {
        try {
            String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customer.getId());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getPostal());
            ps.setString(5, customer.getPhone());
            ps.setInt(6, customer.getDivisionId());
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
            ps.setInt(5, customer.getDivisionId());
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
