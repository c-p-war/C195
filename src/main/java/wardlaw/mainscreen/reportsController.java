package wardlaw.mainscreen;

import helpers.appointmentsUtil;
import helpers.contactUtil;
import helpers.countryUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class reportsController implements Initializable {

    @FXML
    public Button btnCancel;
    @FXML
    public TableView<ReportMonth> tableMonth;
    @FXML
    public TableView<ReportContact> tableSched;
    @FXML
    public TableColumn<ReportMonth, String> colSchedApptId;
    @FXML
    public TableColumn<ReportMonth, String> colSchedTitle;
    @FXML
    public TableColumn<ReportMonth, String> colSchedType;
    @FXML
    public TableColumn<ReportMonth, String> colSchedDesc;
    @FXML
    public TableColumn<ReportMonth, String> colSchedStart;
    @FXML
    public TableColumn<ReportMonth, String> colSchedEnd;
    @FXML
    public TableColumn<ReportMonth, String> colSchedCustId;
    @FXML
    public ComboBox<String> comboContact;
    @FXML
    public TableColumn<ReportMonth, String> colMonth;
    @FXML
    public TableColumn<ReportMonth, Integer> colMonthCount;
    @FXML
    public TableColumn<ReportMonth, String> colMonthType;
    @FXML
    public TableView<ReportCountry> tableCountry;
    @FXML
    public TableColumn<ReportCountry, String> colCountry;
    @FXML
    public TableColumn<ReportCountry, Integer> colCountryCount;

    ObservableList<String> comboListContacts = FXCollections.observableArrayList();

    /**
     * Returns to the previous screen
     *
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
     * Populates the three reports table prior to load-in/by default
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Contact schedule combo box
            for (Contact contact : contactUtil.getContacts()) {
                comboListContacts.add(contact.getName());
            }
            // Setting default contact schedule report
            comboContact.setItems(comboListContacts);
            comboContact.setValue(comboListContacts.get(0));
            selectContact();

            ObservableList<ReportMonth> byMonthList = appointmentsUtil.reportMonths();
            // Appointments By Month
            tableMonth.setItems(byMonthList);
            colMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
            colMonthType.setCellValueFactory(new PropertyValueFactory<>("type"));
            colMonthCount.setCellValueFactory(new PropertyValueFactory<>("count"));
            // Customers By Country
            ObservableList<ReportCountry> custByCountry = countryUtil.getCustByCountry();
            tableCountry.setItems(custByCountry);
            colCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
            colCountryCount.setCellValueFactory(new PropertyValueFactory<>("count"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the desired contact on the Scheduled by Contact tables and updates the table
     *
     * @throws SQLException
     */
    public void selectContact() throws SQLException {
        int contactId = contactUtil.getContactId(comboContact.getValue());
        tableSched.setItems(contactUtil.reportContacts(contactId));
        colSchedApptId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSchedTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colSchedType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colSchedDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colSchedStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        colSchedEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        colSchedCustId.setCellValueFactory(new PropertyValueFactory<>("customerId"));

    }
}
