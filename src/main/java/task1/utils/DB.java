package task1.utils;

import task1.DbAppException;
import task1.FileAppException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {

    // It holds the connection object
    private Connection connection;

    // Database driver and URL s
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:mem:swed;MODE=MySQL;DB_CLOSE_DELAY=-1;IGNORECASE=TRUE";

    // Database credentials
    private String user;
    private String pass;

    /**
     * It calculates the purchase prise percentage according given formula!
     */
    public DB( String user, String pass ) {
        this.user = user;
        this.pass = pass;
        try {
            Class.forName(JDBC_DRIVER);
            this.connection = DriverManager.getConnection(DB_URL, user, pass);
            System.out.println("Database connection established...");
        } catch (ClassNotFoundException | SQLException exception) {
            throw new DbAppException("Unable to connect to the database !", exception);
        }

    }

    public String getUser() {
        return user;
    }

    public void setUser( String user ) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass( String pass ) {
        this.pass = pass;
    }

    /*
     *    Returns the connection object.
     * */

    public Connection getConnection() {

        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, this.user, this.pass);
            }
        } catch (Exception exception) {
            throw new DbAppException("Unable to get connection from database !", exception);
        }
        return connection;
    }

    /***
     *    It reads the DDL file specified in ddl_file_path
     *    Executes it and Returns the connection object.
     * */

    public DB createDatabaseTables( String ddl_file_path ) {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            String sql = Files.readString(Paths.get(ddl_file_path));
            stmt.executeUpdate(sql);
        } catch (IOException exception) {
            throw new FileAppException(String.format("It occured an error while reading %s file!",
                    Paths.get(ddl_file_path)), exception);
        } catch (Exception exception) {
            throw new DbAppException("It occured an error while creating tables!", exception);
        }
        return this;
    }

}