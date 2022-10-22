package wardlaw.mainscreen;

import helpers.contactUtil;
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


    public void cancel(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("appointments.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void save(ActionEvent actionEvent) {
        int id = Integer.parseInt(txtFieldId.getText());
        String title = txtFieldTitle.getText();
        String description = txtFieldDescr.getText();
        String location = txtFieldLoc.getText();
        String contact = comboContact.getValue();
        String type = txtFieldType.getText();
        // TODO: Date/time conversion
//        String start = LocalDateTime.txtFieldStart.getText();
//        String end = txtFieldEnd.getText();
        int customerId = Integer.parseInt(txtFieldCustId.getText());
        int userId = Integer.parseInt(txtFieldUserId.getText());
//        Appointment updateAppointment = new Appointment(id, title, description, location, type, start, end, customerId, userId, contact);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Appointment selectedAppointment = appointmentsController.getSelectedAppointment();
        txtFieldId.setText(String.valueOf(selectedAppointment.getId()));
        txtFieldTitle.setText(selectedAppointment.getTitle());
        txtFieldType.setText(selectedAppointment.getType());
        // TODO: Date/time conversion
        txtFieldStart.setText(String.valueOf(selectedAppointment.getStart()));
        txtFieldEnd.setText(String.valueOf(selectedAppointment.getEnd()));
        try {
            for (Contact contact : contactUtil.getContacts()){
                comboListContacts.add(contact.getName());
            }
            comboContact.setValue(selectedAppointment.getContactName());
            comboContact.setItems(comboListContacts);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        comboContact
        txtFieldLoc.setText(selectedAppointment.getLocation());
        txtFieldDescr.setText(selectedAppointment.getDescription());
        txtFieldCustId.setText(String.valueOf(selectedAppointment.getCustomerId()));
        txtFieldUserId.setText(String.valueOf(selectedAppointment.getUserId()));
        System.out.println(selectedAppointment);
    }
}
