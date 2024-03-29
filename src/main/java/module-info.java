module org.example.ex_application_bureau {

    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires jbcrypt;
    requires json;
    requires java.sql;

    opens org.example.ex_application_bureau to javafx.fxml;
    exports org.example.ex_application_bureau;

}