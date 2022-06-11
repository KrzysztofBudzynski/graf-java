module com.example.grafjava {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.grafjava to javafx.fxml;
    exports com.example.grafjava;
}