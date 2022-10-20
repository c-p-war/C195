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

    // TODO: Remove the sqlException if the sql call is removed
    public static void main(String[] args) throws SQLException {
        // Wrap the launch for javaFX with the DB connections

//        Date date = new Date();
//        long time = date.getTime();
//        Timestamp ts = new Timestamp(time);
//        LocalDateTime lt = LocalDateTime.now();

        JDBC.openConnection();
        contactUtil.getContacts();
//        Customer john = new Customer(5, "John", "123 Main", "23923", "1234567891", 3);

//        Appointment foo = new Appointment(4 , "rrr", "rrr", "rrr", "rrr", lt, lt, 2,1, 1);


        launch();
        JDBC.closeConnection();
    }
}
