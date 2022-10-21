package wardlaw.mainscreen;

import helpers.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();
        launch();
        JDBC.closeConnection();
    }
}
