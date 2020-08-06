package task.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import task1.utils.Constants;
import task1.utils.DB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DBTest {
    DB db;
    final String ddlFileName = "testDatabaseDDL.sql";

    @BeforeAll
    public void initDataBase() throws SQLException {
        db = new DB(Constants.USER,Constants.PASS);
    }

    @Test
    public void tableCreationTest() {

        db.createDatabaseTables("src/test/resources/create_vehicle_table.sql");

        String sql = "SELECT count(*) FROM vehicles";
        try (Statement stmt = db.getConnection().createStatement()){
            ResultSet resultSet = stmt.executeQuery(sql);
            Assertions.assertTrue(resultSet.next());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Assertions.fail("It needs to be select from test_vehicles table");
        }
    }

}
