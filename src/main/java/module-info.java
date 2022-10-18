module wardlaw.c195 {
    requires javafx.controls;
    requires javafx.fxml;


    opens wardlaw.mainscreen to javafx.fxml;
    exports wardlaw.mainscreen;
}
