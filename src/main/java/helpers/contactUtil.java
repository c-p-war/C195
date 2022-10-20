package helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class contactUtil {
    public static ObservableList<Contact> getContacts()throws SQLException {
        String getContacts = "SELECT * FROM contacts";
        PreparedStatement ps = JDBC.connection.prepareCall((getContacts));
        ResultSet rs = ps.executeQuery();
        ObservableList<Contact> contactList = FXCollections.observableArrayList();

        while (rs.next()){
            int id = rs.getInt("Contact_ID");
            String name = rs.getString("Contact_Name");
            String email = rs.getString("Email");
            Contact contact = new Contact(id, name, email);
            contactList.add(contact);
        }
        System.out.println(contactList);
        return contactList;
    }
}
