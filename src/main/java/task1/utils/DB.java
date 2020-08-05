package task1.utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {

    private Connection connection;

    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:mem:swed;MODE=MySQL;DB_CLOSE_DELAY=-1;IGNORECASE=TRUE";


    // Database credentials
    String user;
    String pass;


    public DB( String user, String pass) {
        this.user = user;
        this.pass = pass;
        try {
            Class.forName(JDBC_DRIVER);
            this.connection = DriverManager.getConnection(DB_URL,user,pass);
            System.out.println("Database connection established...");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }

    }

    /*
     *    Returns the connection object.
     * */

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()){
            connection = DriverManager.getConnection(DB_URL,this.user,this.pass);
        }
        return connection;
    }


    /***
     *    It reads the DDL file specified in ddl_file_path
     *    Executes it and Returns the connection object.
     *
     * */

    public DB createDatabaseTables(String ddl_file_path) {
        try( Connection conn = getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = Files.readString(Paths.get(ddl_file_path));
            stmt.executeUpdate(sql);
        } catch (Exception exception){
            System.out.println("System Stopped by: " + exception.getMessage());
        }
        return this;
    }

}