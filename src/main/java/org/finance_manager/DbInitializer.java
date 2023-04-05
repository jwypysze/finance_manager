package org.finance_manager;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbInitializer {
    private final Connection connection;

    public DbInitializer(Connection connection) {
        this.connection = connection;
    }
    public void initDb() throws IOException, SQLException {
        try (InputStream incomes = getClass().getResourceAsStream("/sql/incomes_ddl.sql");
        InputStream expenses = getClass().getResourceAsStream("/sql/expenses_ddl.sql");
        InputStream category = getClass().getResourceAsStream("/sql/category_ddl.sql")) {
            executeSqlFromResource(category);
            executeSqlFromResource(incomes);
            executeSqlFromResource(expenses);
        }
    }

    private void executeSqlFromResource(InputStream inputStream) throws IOException, SQLException {
        if (inputStream == null){
            System.err.println("Opening resource is unavailable");
            return;
        }
        String sql = new String(inputStream.readAllBytes());
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.execute();
    }
}
