module com.example.testing {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.example.testing to javafx.fxml;
    exports com.example.testing;
}