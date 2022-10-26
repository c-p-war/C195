package wardlaw.mainscreen;

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
    private TableColumn<Appointment, String> colApptStart;
    @FXML
    private TableColumn<Appointment, String> colApptEnd;
    @FXML
    private TableColumn<Appointment, Integer> colApptCustId;
    @FXML
    private TableColumn<Appointment, Integer> colApptUserId;
    @FXML
    private RadioButton radioBtnAll;

    public static Appointment selectedAppointment;

    public static Appointment getSelectedAppointment() {
        return selectedAppointment;
    }

    /**
     * Returns the user to the previous screen
     * @param actionEvent
     * @throws IOException
     */
    public void cancel(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainScreen.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Navigates to the update appointment screen
     * @param actionEvent
     * @throws IOException
     */
    public void update(ActionEvent actionEvent) throws IOException {
        selectedAppointment = appointTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            util.stringToError("No appointment selected");
        } else {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("appointmentsUpdate.fxml")));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Navigates to the add appointments screen
     * @param actionEvent
     * @throws IOException
     */
    public void add(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("appointmentsAdd.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Local helper for setting the table columns in various class methods
     */
    public void setColumns() {
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

    /**
     * Sets the table to show appointments for the current week
     * @throws SQLException
     */
    public void getApptsByWeek() throws SQLException {
        if (this.toggleGroupAppt.getSelectedToggle().equals(this.radioBtnWeek)) {
            appointTable.setItems(appointmentsUtil.getWeek());
            setColumns();
        }
    }

    /**
     * Sets the table to show appointments for the current month
     * @throws SQLException
     */
    public void getApptsByMonth() throws SQLException {
        if (this.toggleGroupAppt.getSelectedToggle().equals(this.radioBtnMonth)) {
            appointTable.setItems(appointmentsUtil.getMonth());
            setColumns();
        }
    }

    /**
     * Sets the table to show all appointments by default/on load in
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointTable.setItems(appointmentsUtil.getAppointments());
            setColumns();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the table to show all appointments outside of initalization
     * @throws SQLException
     */
    public void getApptsAll() throws SQLException {
        if (this.toggleGroupAppt.getSelectedToggle().equals(this.radioBtnAll)) {
            appointTable.setItems(appointmentsUtil.getAppointments());
            setColumns();
        }
    }

    /**
     * Removes the selected appointment from the DB
     * @throws SQLException
     */
    public void delete() throws SQLException {
        selectedAppointment = appointTable.getSelectionModel().getSelectedItem();
        appointmentsUtil.deleteAppointment(selectedAppointment);
        appointTable.setItems(appointmentsUtil.getAppointments());
        setColumns();
        util.stringToAlert("The following appointment was deleted:\nAppointment ID: " + selectedAppointment.getId() + "\nType: " + selectedAppointment.getType());
    }
}
