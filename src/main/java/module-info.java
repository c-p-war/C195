module wardlaw.c195 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens wardlaw.mainscreen to javafx.fxml;
    exports wardlaw.mainscreen;
}
