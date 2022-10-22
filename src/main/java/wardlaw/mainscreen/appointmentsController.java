package wardlaw.mainscreen;

import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import helpers.*;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

public class appointmentsController implements Initializable {
    @FXML
    public ToggleGroup toggleGroupAppt;
    @FXML
    public RadioButton radioBtnWeek;
    @FXML
    public RadioButton radioBtnMonth;
    @FXML
    private TableView<Appointment> appointTable;
    @FXML
    private TableColumn<Appointment, Integer> colApptId;
    @FXML
    private TableColumn<Appointment, String> colApptTitle;
    @FXML
    private TableColumn<Appointment, String> colApptDescr;
    @FXML
    private TableColumn<Appointment, String> colApptLocation;
    @FXML
    private TableColumn<Appointment, String> colApptContact;
    @FXML
    private TableColumn<Appointment, String> coldApptType;
    @FXML
    private TableColumn<Appointment, LocalDateTime> colApptStart;
    @FXML
    private TableColumn<Appointment, LocalDateTime> colApptEnd;
    @FXML
    private TableColumn<Appointment, Integer> colApptCustId;
    @FXML
    private TableColumn<Appointment, Integer> colApptUserId;
    @FXML
    private RadioButton radioBtnAll;

    public static Appointment selectedAppointment;
    public static Appointment getSelectedAppointment(){return selectedAppointment;}

    public void cancel(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainScreen.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void update(ActionEvent actionEvent) throws IOException {
        selectedAppointment = appointTable.getSelectionModel().getSelectedItem();
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("appointmentsUpdate.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void add(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("appointmentsAdd.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void getApptsByWeek() throws SQLException {
        if (this.toggleGroupAppt.getSelectedToggle().equals(this.radioBtnWeek)) {
            appointTable.setItems(appointmentsUtil.getWeek());
            colApptId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colApptTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colApptDescr.setCellValueFactory(new PropertyValueFactory<>("description"));
            colApptLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
            colApptContact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            coldApptType.setCellValueFactory(new PropertyValueFactory<>("type"));
            colApptStart.setCellValueFactory(new PropertyValueFactory<>("start"));
            colApptEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
            colApptCustId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            colApptUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        }
    }

    public void getApptsByMonth() throws SQLException {
        if (this.toggleGroupAppt.getSelectedToggle().equals(this.radioBtnMonth)) {
            appointTable.setItems(appointmentsUtil.getMonth());
            colApptId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colApptTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colApptDescr.setCellValueFactory(new PropertyValueFactory<>("description"));
            colApptLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
            colApptContact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            coldApptType.setCellValueFactory(new PropertyValueFactory<>("type"));
            colApptStart.setCellValueFactory(new PropertyValueFactory<>("start"));
            colApptEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
            colApptCustId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            colApptUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointTable.setItems(appointmentsUtil.getAppointments());
            colApptId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colApptTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colApptDescr.setCellValueFactory(new PropertyValueFactory<>("description"));
            colApptLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
            colApptContact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            coldApptType.setCellValueFactory(new PropertyValueFactory<>("type"));
            colApptStart.setCellValueFactory(new PropertyValueFactory<>("start"));
            colApptEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
            colApptCustId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            colApptUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getApptsAll() throws SQLException {
        if (this.toggleGroupAppt.getSelectedToggle().equals(this.radioBtnAll)) {
            appointTable.setItems(appointmentsUtil.getAppointments());
            colApptId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colApptTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colApptDescr.setCellValueFactory(new PropertyValueFactory<>("description"));
            colApptLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
            colApptContact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            coldApptType.setCellValueFactory(new PropertyValueFactory<>("type"));
            colApptStart.setCellValueFactory(new PropertyValueFactory<>("start"));
            colApptEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
            colApptCustId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            colApptUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        }
    }

    public void delete() throws SQLException {
        selectedAppointment = appointTable.getSelectionModel().getSelectedItem();
        appointmentsUtil.deleteAppointment(selectedAppointment);
        appointTable.setItems(appointmentsUtil.getAppointments());
        colApptId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colApptTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colApptDescr.setCellValueFactory(new PropertyValueFactory<>("description"));
        colApptLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colApptContact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        coldApptType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colApptStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        colApptEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        colApptCustId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colApptUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));

    }
}
