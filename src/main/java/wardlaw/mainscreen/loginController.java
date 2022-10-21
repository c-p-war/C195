package wardlaw.mainscreen;

import helpers.appointmentsUtil;
import helpers.util;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class loginController {
    public Button btnLogin;
    public TextField txtFieldUsername;
    public TextField txtFieldPassword;

    public void login(ActionEvent actionEvent) throws IOException, SQLException {
        String userName = txtFieldUsername.getText();
        String password = txtFieldPassword.getText();
        if (util.checkUsers(userName, password).size() > 0){
            writeActivity("Successful Login - User: Test | Time: ");
            ObservableList<Appointment> comingSoon = appointmentsUtil.getFifteen();
            if(comingSoon.size() > 0){
                Appointment alert = comingSoon.get(0);
                int id = alert.getId();
                // TODO: Verify time conversion
                LocalDateTime start = alert.getStart();
                String txt = "\n\nAppointment within 15 minutes.\nID: " + id + "\nStart Date/Time: " + start;
                util.stringToAlert(txt);
            }
            // Navigate to main
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainScreen.fxml")));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else {
            util.stringToError("Unsuccessful login.\n\n Try again");
            writeActivity("Unsuccessful Login - User: Test | Time: ");
        }
    }

    private void writeActivity(String loginText) {
        try (FileWriter fileWriter = new FileWriter("login_activity.txt", true)) {
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat timeFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
            fileWriter.write(loginText + timeFormat.format(date) + "\n");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
