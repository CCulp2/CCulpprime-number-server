module com.cculp.primenumberserver {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.cculp.primenumberserver to javafx.fxml;
    exports com.cculp.primenumberserver;
}