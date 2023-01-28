package wardlaw.mainscreen;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;


public class customerController implements Initializable {
    @FXML
    public TextField textSearch;
    @FXML
    private TableView<Customer> customersTable;
    @FXML
    private TableColumn<Customer, String> colCustName;
    @FXML
    private TableColumn<Customer, String> colCustCountry;
    @FXML
    private TableColumn<Customer, String> colCustDivision;
    @FXML
    private TableColumn<Customer, String> colCustAddress;
    @FXML
    private TableColumn<Customer, String> colCustPostal;
    @FXML
    private TableColumn<Customer, String> colCustPhone;

    private ObservableList<Customer> customerList = FXCollections.observableArrayList();


    public static Customer selectedCustomer;

    public static Customer getSelectedCustomer() {
        return selectedCustomer;
    }

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
     * Navigates to the update customer form
     *
     * @param actionEvent
     * @throws IOException
     */
    public void update(ActionEvent actionEvent) throws IOException {
        selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            util.stringToError("No customer selected");
        } else {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("customerUpdate.fxml")));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Navigates to the add customer form
     *
     * @param actionEvent
     * @throws IOException
     */
    public void add(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("customerAdd.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sets the table to show all customers on default/load in
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            customerList = customerUtil.getCustomers();
            customersTable.setItems(customerList);
            colCustName.setCellValueFactory(new PropertyValueFactory<>("name"));

            colCustDivision.setCellValueFactory(new PropertyValueFactory<>("division"));
            colCustCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
            colCustAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            colCustPostal.setCellValueFactory(new PropertyValueFactory<>("postal"));
            colCustPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void searchCustomers() throws SQLException {
        String name = textSearch.getText();
        customerList = customerUtil.searchCustomers(name);
        customersTable.setItems(customerList);
    }

    /**
     * Removes the selected customer from the DB
     *
     * @throws SQLException
     */
    public void delete() throws SQLException {
        selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        customerUtil.deleteCustomer(selectedCustomer);
        customerList = customerUtil.getCustomers();
        customersTable.setItems(customerList);
        colCustName.setCellValueFactory(new PropertyValueFactory<>("name"));

        colCustDivision.setCellValueFactory(new PropertyValueFactory<>("division"));
        colCustCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        colCustAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCustPostal.setCellValueFactory(new PropertyValueFactory<>("postal"));
        colCustPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        util.stringToAlert("Customer, " + selectedCustomer.getName() + ", was deleted");
    }
}
