module com.transformasi.transformasi {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.transformasi.transformasi to javafx.fxml;
    exports com.transformasi.transformasi;
}