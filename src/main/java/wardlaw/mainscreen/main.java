package wardlaw.mainscreen;

import helpers.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;

public class main extends Application {
    /**
     * Starts the application and navigates to the login screen
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Scheduling App");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Opens the DB connection
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {
//        Locale.setDefault(new Locale("fr"));
        JDBC.openConnection();
        launch();
        JDBC.closeConnection();
    }
}
