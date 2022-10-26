package wardlaw.mainscreen;

import helpers.appointmentsUtil;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class loginController implements Initializable {
    @FXML
    public Button btnLogin;
    @FXML
    public TextField txtFieldUsername;
    @FXML
    public TextField txtFieldPassword;
    @FXML
    public Label labelZoneId;
    @FXML
    public Label labelUserName;
    @FXML
    public Label labelPassword;

    public static ResourceBundle rb = ResourceBundle.getBundle("bundle/lang", Locale.getDefault());
    private final ObservableList<User> userList = FXCollections.observableArrayList();


    public void login(ActionEvent actionEvent) throws IOException, SQLException {
        String userName = txtFieldUsername.getText();
        String password = txtFieldPassword.getText();
        for (User user : util.checkUsers(userName, password)) {
            userList.add(user);
        }

        if (userList.size() > 0) {
            int userId = userList.get(0).getId();
            writeActivity("Successful Login - User: Test | Time: ");
            // Returns all appointments tied to a user id
            ObservableList<Appointment> userAppointments = appointmentsUtil.getAppointmentsByUser(userId);
            Appointment comingSoon = null;
            boolean noAlerts = true;
            for (Appointment a : userAppointments){
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime rangeTop = now.plusMinutes(15);
                LocalDateTime ldtStart = util.getLdtFromString(a.getStart());
                if (ldtStart.isAfter(now) && ldtStart.isBefore(rangeTop)){
                    comingSoon = a;
                    noAlerts = false;
                    int id = comingSoon.getId();
                    String start = comingSoon.getStart();
                    // LAMBDA #1
                    if (Locale.getDefault().getLanguage().equals("fr")){
                        alertInterface withinFifteen = () -> "\n\nRendez-vous dans les 15 minutes.\nID: " + id + "\nDate/heure de début: " + start;
                        util.stringToAlert(withinFifteen.alert());
                    }else {
                        alertInterface withinFifteen = () -> "\n\nAppointment within 15 minutes.\nID: " + id + "\nStart Date/Time: " + start;
                        util.stringToAlert(withinFifteen.alert());
                    }
                }
            }
            if (noAlerts){
                util.stringToAlert("No upcoming appointments");
            }
            // Navigate to main
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainScreen.fxml")));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else {
            // TODO: Pass in locale information to the util method
            util.stringToError("Unsuccessful login.\n\n Try again");
            writeActivity("Unsuccessful Login - User: Test | Time: ");
        }
    }

    // TODO: Refactor
    private void writeActivity(String loginText) {
        try (FileWriter fileWriter = new FileWriter("login_activity.txt", true)) {
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat timeFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
            fileWriter.write(loginText + timeFormat.format(date) + "\n");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Locale.getDefault().getLanguage().equals("fr")) {
            labelUserName.setText(rb.getString("userName"));
            labelPassword.setText(rb.getString("password"));
            btnLogin.setText(rb.getString("login"));
        }
    }
}
