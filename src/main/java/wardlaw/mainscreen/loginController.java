package wardlaw.mainscreen;

import helpers.appointmentsUtil;
import helpers.util;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;

public class loginController {
    public void login(ActionEvent actionEvent) throws IOException, SQLException {
        // TODO: Tie userName and in_password to textfields
        int login = util.checkUsers("test", "test").size();
        if (login > 0){
            System.out.println("Login successful");
            ObservableList<Appointment> comingSoon = appointmentsUtil.getFifteen();
            if(comingSoon.size() > 0){
                Appointment alert = comingSoon.get(0);
                int id = alert.getId();
                // TODO: Verify time conversion
                LocalDateTime start = alert.getStart();
                String txt = "\n\nAppointment within 15 minutes.\nID: " + id + "\nStart Date/Time: " + start;
                System.out.println(txt);
            }
            // Navigate to main
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainScreen.fxml")));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else if (login < 0){
            // TODO: Add alert
            util.stringToError("Unsuccessful login.\n\n Try again");
        }
    }
}
