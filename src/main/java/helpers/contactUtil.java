package helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.ReportContact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Utility for the Contact object. Holds SQL statements.
 */
public class contactUtil {
    /**
     * @return All contacts from the db
     * @throws SQLException
     */
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

    /**
     * @param contactId
     * @return Appointments for the report 'Contact Schedule' based on the provided contactId
     * @throws SQLException
     */
    public static ObservableList<ReportContact> reportContacts(int contactId) throws SQLException {
        String reportContactAppts = "SELECT Appointment_ID, Title, Type, Description, Start, End, Customer_ID FROM appointments WHERE Contact_ID = ?;";
        PreparedStatement ps = JDBC.connection.prepareCall((reportContactAppts));
        ps.setInt(1, contactId);
        ResultSet rs = ps.executeQuery();
        ObservableList<ReportContact> contactReport = FXCollections.observableArrayList();

        while (rs.next()) {
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String type = rs.getString("Type");
            String description = rs.getString("Description");
            String start = util.utcToLocal(rs.getTimestamp("Start").toLocalDateTime());
            String end = util.utcToLocal(rs.getTimestamp("End").toLocalDateTime());
            int customerId = rs.getInt("Customer_ID");
            ReportContact appt = new ReportContact(id, title, type, description, start, end, customerId);
            contactReport.add(appt);
        }
        return contactReport;
    }

    /**
     * @param contactId
     * @return Contact name based on the provided contactId
     * @throws SQLException
     */
    public static String getContactName(int contactId) throws SQLException {
        String sql = "SELECT Contact_Name FROM contacts WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareCall((sql));
        ps.setInt(1, contactId);
        ResultSet rs = ps.executeQuery();
        String contactName = null;
        while (rs.next()) {
            contactName = rs.getString("Contact_Name");
        }
        return contactName;
    }

    /**
     * @param contactName
     * @return Contact id based on the provided contactName
     * @throws SQLException
     */
    public static int getContactId(String contactName) throws SQLException {
        String sql = "SELECT Contact_ID FROM contacts WHERE Contact_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareCall((sql));
        ps.setString(1, contactName);
        ResultSet rs = ps.executeQuery();
        int contactId = 0;
        while (rs.next()) {
            contactId = rs.getInt("Contact_ID");
        }
        return contactId;
    }
}
