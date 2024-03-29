package org.example.ex_application_bureau.Model;

import org.example.ex_application_bureau.Util.Database;
import java.sql.Connection;
import java.sql.SQLException;

public class StoredProcedure {

    private final Connection connection;

    //constructeur
    public StoredProcedure() throws SQLException {

        this.connection = Database.connectionToDB();
    }


}
