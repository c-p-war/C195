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

public class appointmentsAddController implements Initializable {
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
     * LAMBDA 2
     * Auto increments the appointment id via Lambda expression. Populates the id field, and contacts combo box
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // LAMBDA #2
            incrementInterface intStart = n -> n + 1;
            int apptCount = appointmentsUtil.getAppointments().size();
            txtFieldId.setText(String.valueOf(intStart.increment(apptCount)));
            for (Contact contact : contactUtil.getContacts()) {
                comboListContacts.add(contact.getName());
            }
            comboContact.setItems(comboListContacts);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Places the appointment in the DB. Performs input validation
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void submit(ActionEvent actionEvent) throws IOException, SQLException {
        int id = Integer.parseInt(txtFieldId.getText());
        String title = txtFieldTitle.getText();
        String description = txtFieldDescr.getText();
        String location = txtFieldLoc.getText();
        String contact = comboContact.getValue();
        String type = txtFieldType.getText();
        LocalDateTime ldt_start = util.getLdtFromString(txtFieldStart.getText());
        LocalDateTime ldt_end = util.getLdtFromString(txtFieldEnd.getText());

        String start = util.localToUTC(ldt_start);
        String end = util.localToUTC(ldt_end);

        int customerId = Integer.parseInt(txtFieldCustId.getText());
        int userId = Integer.parseInt(txtFieldUserId.getText());
        int zdt_start = util.localToEST(ldt_start).getHour();
        int zdt_end = util.localToEST(ldt_end).getHour();
        ObservableList<Appointment> appointments = customerUtil.getCustomerAppointments(customerId);
        for (Appointment a : appointments) {
            LocalDateTime a_start = util.getLdtFromString(a.getStart());
            LocalDateTime a_end = util.getLdtFromString(a.getEnd());
            if (a_start.isBefore(ldt_end) && (ldt_start.isBefore(a_end))) {
                util.stringToAlert("Block already booked");
            }
        }
        if (zdt_start < 8 || zdt_start >= 22 || zdt_end < 8 || zdt_end >= 22) {
            util.stringToError("Time must be between 8:00AM EST and 10:00PM EST");
        } else {
            Appointment newAppointment = new Appointment(id, title, description, location, type, start, end, customerId, userId, contact);
            appointmentsUtil.addAppointment(newAppointment);
            cancel(actionEvent);
        }
    }
}
