package task1.utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {

    private static DB instance;
    private Connection connection;

    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:mem:swed";
    static final String RESOURCE_PATH = "src/main/resources/";
    static final String DDL_FILE = "database.sql";


    // Database credentials
    static final String USER = "sa";
    static final String PASS = "";

    // Private constructor for singleton pattern
    private DB() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
            this.connection = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Database connection established...");
        } catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }
    }

    /*
     *    Returns the connection object.
     * */

    public Connection getConnection() {
        return connection;
    }

    /*
    *    Returns the database instance.
    * */

    public static DB getInstance() throws SQLException {
        if (instance == null) {
            instance = new DB();
        } else if (instance.getConnection().isClosed()) {
            instance = new DB();
        }
        return instance;
    }

    /***
     *    It reads the DDL file specified in RESOURCE_PATH + DDL_FILE
     *    Executes it and Returns the connection object.
     *
     * */

    public DB createDatabaseTables() {
        try( Connection conn = getInstance().getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = Files.readString(Paths.get(RESOURCE_PATH + DDL_FILE));
            stmt.executeUpdate(sql);
        } catch (Exception exception){
            System.out.println("System Stopped by: " + exception.getMessage());
        }
        return this;
    }

}