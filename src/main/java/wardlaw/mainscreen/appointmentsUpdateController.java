package wardlaw.mainscreen;

import helpers.appointmentsUtil;
import helpers.contactUtil;
import helpers.customerUtil;
import helpers.util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

public class appointmentsUpdateController implements Initializable {
    @FXML
    public TextField txtFieldId;
    @FXML
    public TextField txtFieldTitle;
    @FXML
    public TextField txtFieldType;
    @FXML
    public TextField txtFieldStart;
    @FXML
    public ComboBox<String> comboContact;
    @FXML
    public TextField txtFieldLoc;
    @FXML
    public TextField txtFieldDescr;
    @FXML
    public TextField txtFieldEnd;
    @FXML
    public TextField txtFieldCustId;
    @FXML
    public TextField txtFieldUserId;
    ObservableList<String> comboListContacts = FXCollections.observableArrayList();

    /**
     * Returns the user to the previous screen
     *
     * @param actionEvent
     * @throws IOException
     */
    public void cancel(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("appointments.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sends the updated appointment to the DB. Performs input validation.
     *
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void save(ActionEvent actionEvent) throws IOException, SQLException {
        int id = Integer.parseInt(txtFieldId.getText());
        String title = txtFieldTitle.getText();
        String description = txtFieldDescr.getText();
        String location = txtFieldLoc.getText();
        String contact = comboContact.getValue();
        String type = txtFieldType.getText();
        LocalDateTime start = util.getLdtFromString(txtFieldStart.getText());
        LocalDateTime end = util.getLdtFromString(txtFieldEnd.getText());
        int customerId = Integer.parseInt(txtFieldCustId.getText());
        int userId = Integer.parseInt(txtFieldUserId.getText());
        int zdt_start = util.localToEST(start).getHour();
        int zdt_end = util.localToEST(end).getHour();
        ObservableList<Appointment> appointments = customerUtil.getCustomerAppointments(customerId);
        boolean overlap = false;
        boolean outOfOffice = false;
        // Set overlap
        for (Appointment a : appointments) {
            if (a.getId() != id) {
                LocalDateTime a_start = util.getLdtFromString(a.getStart());
                LocalDateTime a_end = util.getLdtFromString(a.getEnd());
                if (a_start.isBefore(end) && (start.isBefore(a_end))) {
                    overlap = true;
                }
            }
        }
        // Set outOfOffice
        if (zdt_start < 8 || zdt_start >= 22 || zdt_end < 8 || zdt_end >= 22) {
            outOfOffice = true;
        }
        // Check overlap
        if (overlap) {
            util.stringToError("Appointment overlap");
        }
        // Check out of office
        if (outOfOffice) {
            util.stringToError("Times must be between 8AM EST and 10PM EST");
        }
        // Update appointment if conditions are met
        if (!overlap && !outOfOffice) {
            // Convert to UTC for DB storage
            String utc_start = util.localToUTC(start);
            String utc_end = util.localToUTC(end);
            Appointment newAppointment = new Appointment(id, title, description, location, type, utc_start, utc_end, customerId, userId, contact);
            appointmentsUtil.updateAppointment(newAppointment);
            cancel(actionEvent);
        }
    }

    /**
     * Sets the text fields for the update appointment form based on the selected appointment from the previous screen
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Appointment selectedAppointment = appointmentsController.getSelectedAppointment();
        txtFieldId.setText(String.valueOf(selectedAppointment.getId()));
        txtFieldTitle.setText(selectedAppointment.getTitle());
        txtFieldType.setText(selectedAppointment.getType());
        txtFieldStart.setText(selectedAppointment.getStart());
        txtFieldEnd.setText(selectedAppointment.getEnd());
        try {
            for (Contact contact : contactUtil.getContacts()) {
                comboListContacts.add(contact.getName());
            }
            comboContact.setValue(selectedAppointment.getContactName());
            comboContact.setItems(comboListContacts);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        txtFieldLoc.setText(selectedAppointment.getLocation());
        txtFieldDescr.setText(selectedAppointment.getDescription());
        txtFieldCustId.setText(String.valueOf(selectedAppointment.getCustomerId()));
        txtFieldUserId.setText(String.valueOf(selectedAppointment.getUserId()));
    }
}
