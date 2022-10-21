package helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.ReportContact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class contactUtil {
    public static ObservableList<Contact> getContacts() throws SQLException {
        String getContacts = "SELECT * FROM contacts";
        PreparedStatement ps = JDBC.connection.prepareCall((getContacts));
        ResultSet rs = ps.executeQuery();
        ObservableList<Contact> contactList = FXCollections.observableArrayList();

        while (rs.next()) {
            int id = rs.getInt("Contact_ID");
            String name = rs.getString("Contact_Name");
            String email = rs.getString("Email");
            Contact contact = new Contact(id, name, email);
            contactList.add(contact);
        }
        System.out.println(contactList);
        return contactList;
    }

    // TODO: Lambda? Integrate the method above?
    public static ObservableList<ReportContact> reportContacts() throws SQLException {
        String reportContactAppts = "SELECT Appointment_ID, Title, Type, Description, Start, ?, Customer_ID FROM appointments WHERE Contact_ID = ?;";
        PreparedStatement ps = JDBC.connection.prepareCall((reportContactAppts));
        ps.setString(1, "End");
        ResultSet rs = ps.executeQuery();
        ObservableList<ReportContact> contactReport = FXCollections.observableArrayList();

        while (rs.next()) {
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String type = rs.getString("Type");
            String description = rs.getString("Description");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            ReportContact appt = new ReportContact(id, title, type, description, start, end, customerId);
            contactReport.add(appt);
        }
        System.out.println(contactReport);
        return contactReport;
    }

    public static String getContactName(int contactId) throws SQLException{
        String sql = "SELECT Contact_Name FROM contacts WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareCall((sql));
        ps.setInt(1, contactId);
        ResultSet rs = ps.executeQuery();
        String contactName = null;
        while (rs.next()){
            contactName = rs.getString("Contact_Name");
        }
        return contactName;
    }

    public static int getContactId(String contactName) throws SQLException{
        String sql = "SELECT Contact_ID FROM contacts WHERE Contact_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareCall((sql));
        ps.setString(1, contactName);
        ResultSet rs = ps.executeQuery();
        int contactId = 0;
        while (rs.next()){
            contactId = rs.getInt("Contact_ID");
        }
        return contactId;
    }
}
