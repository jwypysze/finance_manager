package org.finance_manager;

import org.finance_manager.service.DemoApp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME_ENV = "DB_NAME";
    private static final String DB_USER_ENV = "DB_USER";
    private static final String DB_PASSWORD_ENV = "DB_PASSWORD";
    private static final Scanner SCANNER = new Scanner(System.in);
    public static void main(String[] args) throws IOException, SQLException {
        try (final Connection connection = DriverManager.getConnection
                (JDBC_URL + System.getenv(DB_NAME_ENV), System.getenv(DB_USER_ENV), System.getenv(DB_PASSWORD_ENV));) {
            DbInitializer dbInitializer = new DbInitializer(connection);
            dbInitializer.initDb();
          DemoApp.play();
        }




    }
}
