package wardlaw.mainscreen;

import helpers.appointmentsUtil;
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
import model.Contact;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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


    public void cancel(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("appointments.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // LAMBDA #2
            incrementInterface intStart = n -> n + 1;
            int apptCount = appointmentsUtil.getAppointments().size();
            txtFieldId.setText(String.valueOf(intStart.increment(apptCount)));
            for (Contact contact : contactUtil.getContacts()){
                comboListContacts.add(contact.getName());
            }
            comboContact.setItems(comboListContacts);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void submit(ActionEvent actionEvent) {
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
    }
}
