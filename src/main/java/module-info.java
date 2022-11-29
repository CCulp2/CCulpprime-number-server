module com.cculp.primenumberserver {
    requires javafx.controls;


    opens com.cculp.primenumberserver to javafx.fxml;
    exports com.cculp.primenumberserver;
}