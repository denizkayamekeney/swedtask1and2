package task1.dao;

import task1.DbAppException;
import task1.dto.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiclesDaoImpl implements VehiclesDao {
    // It holds the connection object to database.
    Connection connection;

    public VehiclesDaoImpl( Connection connection ) {
        this.connection = connection;
    }

    /**
     * It inserts the coming vehicle object to vehicles table.
     */

    @Override
    public boolean insert( Vehicle vehicle ) {
        String SQL = "INSERT INTO VEHICLES(id, plate_number, first_registration, purchase_prise, producer, milage, " +
                "previous_indemnity, casco_without_indemnity, casco_with_indemnity) "
                + "VALUES(?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement pstmt = connection.prepareStatement(SQL)) {

            pstmt.setInt(1, vehicle.getId());
            pstmt.setString(2, vehicle.getPlateNumber());
            pstmt.setInt(3, vehicle.getFirstRegistration());
            pstmt.setDouble(4, vehicle.getPurchasePrise());
            pstmt.setString(5, vehicle.getProducer());
            pstmt.setInt(6, vehicle.getMilage());
            pstmt.setDouble(7, vehicle.getPreviousIndemnity());
            pstmt.setDouble(8, vehicle.getCascoWithoutIndemnity());
            pstmt.setDouble(9, vehicle.getCascoWithIndemnity());

            int affectedRows = pstmt.executeUpdate();
        } catch (Exception exception) {
            throw new DbAppException("Unable insert into vehicle table", exception);
        }
        return true;
    }

    /**
     * It updates the existing record in vehicles table.
     */
    @Override
    public boolean update( Vehicle vehicle ) {
        String SQL = "UPDATE VEHICLES SET plate_number=? , " +
                "first_registration=?, purchase_prise=?, producer=?, " +
                "milage=?, previous_indemnity=?, casco_without_indemnity=? , casco_with_indemnity=? " +
                "WHERE id =?  ";

        try (PreparedStatement pstmt = connection.prepareStatement(SQL)) {

            pstmt.setString(1, vehicle.getPlateNumber());
            pstmt.setInt(2, vehicle.getFirstRegistration());
            pstmt.setDouble(3, vehicle.getPurchasePrise());
            pstmt.setString(4, vehicle.getProducer());
            pstmt.setInt(5, vehicle.getMilage());
            pstmt.setDouble(6, vehicle.getPreviousIndemnity());
            pstmt.setDouble(7, vehicle.getCascoWithoutIndemnity());
            pstmt.setDouble(8, vehicle.getCascoWithIndemnity());
            pstmt.setInt(9, vehicle.getId());
            int affectedRows = pstmt.executeUpdate();
        } catch (SQLException exception) {
            throw new DbAppException("Unable update vehicle table", exception);
        }
        return true;
    }

    /**
     * It rens all data as a list from Vehicles table.
     */
    @Override
    public List<Vehicle> findAll() {
        String sql = "SELECT * FROM vehicles";
        List<Vehicle> vehicles = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                vehicles.add(createVehicleORM(resultSet));
            }
        } catch (SQLException exception) {
            throw new DbAppException("Unable select from vehicle table", exception);
        }
        return vehicles;
    }

    /**
     * It returns the vehicle if exists in Vehicles table by given id.
     */
    @Override
    public Vehicle findById( int id ) {
        String sql = "SELECT * FROM VEHICLES WHERE id=?";
        Vehicle vehicle = null;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next())
                vehicle = createVehicleORM(resultSet);
        } catch (SQLException exception) {
            throw new DbAppException("Unable select from vehicle table", exception);
        }
        return vehicle;
    }

    /**
     * It creates a Vehicle object from resultset.
     */
    private Vehicle createVehicleORM( ResultSet resultSet ){
        try {
            Vehicle vehicle = null;
            vehicle = new Vehicle();
            vehicle.setId(resultSet.getInt("id"));
            vehicle.setPlateNumber(resultSet.getString("plate_number"));
            vehicle.setFirstRegistration(resultSet.getInt("first_registration"));
            vehicle.setPurchasePrise(resultSet.getInt("purchase_prise"));
            vehicle.setProducer(resultSet.getString("producer"));
            vehicle.setMilage(resultSet.getInt("milage"));
            vehicle.setPreviousIndemnity(resultSet.getInt("previous_indemnity"));
            vehicle.setCascoWithoutIndemnity(resultSet.getDouble("casco_without_indemnity"));
            vehicle.setCascoWithIndemnity(resultSet.getDouble("casco_with_indemnity"));
            return vehicle;
        } catch (Exception exception){
            throw new DbAppException("Unable parse from result set", exception);
        }
    }
}
