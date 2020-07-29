package task1;

import task1.utils.DB;

import java.sql.SQLException;

public class App {
    public static void main( String[] args ) throws SQLException {
        DB.getInstance().createDatabaseTables();
        System.out.println("Tables are created...");
    }
}
