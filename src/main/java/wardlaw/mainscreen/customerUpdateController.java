package wardlaw.mainscreen;

import helpers.countryUtil;
import helpers.customerUtil;
import helpers.divisionUtil;
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
import model.Country;
import model.Customer;
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class customerUpdateController implements Initializable {
    @FXML
    public TextField txtFieldCustId;
    @FXML
    public TextField txtFieldCustName;
    @FXML
    public ComboBox<String> comboCustCountry;
    @FXML
    public ComboBox<String> comboCustDivision;
    @FXML
    public TextField txtFieldCustAddress;
    @FXML
    public TextField txtFieldCustPostal;
    @FXML
    public TextField txtFieldCustPhone;

    ObservableList<String> comboListCountries = FXCollections.observableArrayList();
    ObservableList<String> comboListDivisions = FXCollections.observableArrayList();

    public void cancel(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("customer.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Customer selectedCustomer = customerController.getSelectedCustomer();
        txtFieldCustId.setText(String.valueOf(selectedCustomer.getId()));
        txtFieldCustName.setText(String.valueOf(selectedCustomer.getName()));
        try {
            for (Country country : countryUtil.getCountries()) {
                comboListCountries.add(country.getCountry());
            }
            comboCustCountry.setItems(comboListCountries);
            // TODO: Reduce cognitive complexity
            comboCustCountry.setValue(customerUtil.getCountryName(customerUtil.getDivisionId(selectedCustomer.getDivision())));
            for (Division division : divisionUtil.getDivByCountry(customerUtil.getCountryId(customerUtil.getDivisionId(selectedCustomer.getDivision())))) {
                comboListDivisions.add(division.getName());
            }
            comboCustDivision.setItems(comboListDivisions);
            comboCustDivision.setValue(selectedCustomer.getDivision());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        txtFieldCustAddress.setText(String.valueOf(selectedCustomer.getAddress()));
        txtFieldCustPostal.setText(String.valueOf(selectedCustomer.getPostal()));
        txtFieldCustPhone.setText(String.valueOf(selectedCustomer.getPostal()));
    }

    public void save(ActionEvent actionEvent) throws SQLException, IOException {
        int id = Integer.parseInt(txtFieldCustId.getText());
        String name = txtFieldCustName.getText();
        String country = comboCustCountry.getValue();
        String division = comboCustDivision.getValue();
        String address = txtFieldCustAddress.getText();
        String postal = txtFieldCustPostal.getText();
        String phone = txtFieldCustPostal.getText();
        Customer updatedCustomer = new Customer(id, name, address, postal, phone, division, country);
        customerUtil.updateCustomer(updatedCustomer);
        cancel(actionEvent);
    }

    public void setDivisions() throws SQLException {
        comboCustDivision.getItems().clear();
        for (Division division : divisionUtil.getDivByCountry(countryUtil.getCountryId(comboCustCountry.getValue()))) {
            comboListDivisions.add(division.getName());
        }
    }
}
