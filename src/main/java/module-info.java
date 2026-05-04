module org.example.trongame {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.trongame to javafx.fxml;
    exports org.example.trongame;
}