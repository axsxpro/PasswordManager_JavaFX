module org.example.ex_application_bureau {

    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires jbcrypt;
    requires json;
    requires java.sql;
    requires java.dotenv;

    opens org.example.ex_application_bureau.Controller to javafx.fxml;
    opens org.example.ex_application_bureau.View to javafx.fxml;
    exports org.example.ex_application_bureau;
    exports org.example.ex_application_bureau.Controller;
    opens org.example.ex_application_bureau.Model to javafx.base;

}