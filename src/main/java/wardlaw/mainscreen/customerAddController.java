package wardlaw.mainscreen;

import helpers.countryUtil;
import helpers.customerUtil;
import helpers.divisionUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class customerAddController implements Initializable {

    public TextField txtFieldCustId;
    public TextField txtFieldCustName;
    public ComboBox<String> comboCustCountry;
    public ComboBox<String> comboCustDivision;
    public TextField txtFieldCustAddress;
    public TextField txtFieldCustPostal;
    public TextField txtFieldCustPhone;
    ObservableList<String> comboListCountries = FXCollections.observableArrayList();
    ObservableList<String> comboListDivisions = FXCollections.observableArrayList();

    /**
     * Returns to the previous screen
     *
     * @param actionEvent
     * @throws IOException
     */
    public void cancel(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("customer.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * LAMBDA 2 - Increments the customer ID. Justification: Reusability for the Appointments ID incrementation
     * Populates the id field, country combo box, and division combo box.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            incrementInterface intStart = n -> n + 1;
            int customerCount = customerUtil.getCustomers().size();
            txtFieldCustId.setText(String.valueOf(intStart.increment(customerCount)));
            for (Country country : countryUtil.getCountries()) {
                comboListCountries.add(country.getCountry());
            }
            comboCustCountry.setItems(comboListCountries);
            for (Division division : divisionUtil.getDivisions()) {
                comboListDivisions.add(division.getName());
            }
            comboCustDivision.setItems(comboListDivisions);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the value options for the division combo box
     *
     * @throws SQLException
     */
    public void setDivisions() throws SQLException {
        comboCustDivision.getItems().clear();
        for (Division division : divisionUtil.getDivByCountry(countryUtil.getCountryId(comboCustCountry.getValue()))) {
            comboListDivisions.add(division.getName());
        }
    }

    /**
     * Sends the new customer to the DB
     *
     * @param actionEvent
     * @throws IOException
     */
    public void submit(ActionEvent actionEvent) throws IOException {
        int id = Integer.parseInt(txtFieldCustId.getText());
        String name = txtFieldCustName.getText();
        String country = comboCustCountry.getValue();
        String division = comboCustDivision.getValue();
        String address = txtFieldCustAddress.getText();
        String postal = txtFieldCustPostal.getText();
        String phone = txtFieldCustPostal.getText();
        Customer newCustomer = new Customer(id, name, address, postal, phone, division, country);
        customerUtil.addCustomer(newCustomer);
        cancel(actionEvent);
    }
}
