package wardlaw.mainscreen;

import helpers.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class loginController {
    public void login(ActionEvent actionEvent) throws IOException {
        // Testing helpers tie in
        /* helpers.contNameToId("Foo");
        helpers.contIdToName(1);
        helpers.userNameToId("Foo");
        helpers.userIdToName(1);
        helpers.getDivisionsByCountry(1);
        helpers.getCountryByDivision(1);
        helpers.convertLocal("System");
        helpers.convertLocal("UTC");
        helpers.convertUTC("System");
        helpers.convertUTC("Local");
        helpers.convertSystem("UTC");
        helpers.convertSystem("Local"); */

        // TODO: Place login logic and tie to successfulLogin
        boolean successfulLogin = true;
        // TODO: If login is successful, navigate to main
        if (successfulLogin){
            // Navigate to main
            System.out.println("navigate to main");
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainScreen.fxml")));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else if (!successfulLogin){
            util.stringToError("Unsuccessful login.\n\n Try again");
        }
    }
}
