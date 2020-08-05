package task1.dao;

import task1.dto.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiclesDaoImpl implements VehiclesDao {
    Connection connection;

    public VehiclesDaoImpl( Connection connection ) {
        this.connection = connection;
    }

    @Override
    public boolean insert( Vehicle vehicle ) throws SQLException {

        String SQL = "INSERT INTO VEHICLES(id, plate_number, first_registration, purchase_prise, producer, milage, previous_indemnity) "
                + "VALUES(?,?,?,?,?,?,?)";

        try (PreparedStatement pstmt = connection.prepareStatement(SQL)) {

            pstmt.setInt(1, vehicle.getId());
            pstmt.setString(2, vehicle.getPlateNumber());
            pstmt.setInt(3, vehicle.getFirstRegistration());
            pstmt.setDouble(4, vehicle.getPurchasePrise());
            pstmt.setString(5, vehicle.getProducer());
            pstmt.setInt(6, vehicle.getMilage());
            pstmt.setDouble(7, vehicle.getPreviousIndemnity());

            int affectedRows = pstmt.executeUpdate();
        }
        return true;
    }

    @Override
    public boolean update( Vehicle vehicle ) {
        String SQL = "UPDATE VEHICLES SET plate_number=? , " +
                "first_registration=?, purchase_prise=?, producer=?, " +
                "milage=?, previous_indemnity=? " +
                "WHERE id =?  ";

        try (PreparedStatement pstmt = connection.prepareStatement(SQL)) {

            pstmt.setString(1, vehicle.getPlateNumber());
            pstmt.setInt(2, vehicle.getFirstRegistration());
            pstmt.setDouble(3, vehicle.getPurchasePrise());
            pstmt.setString(4, vehicle.getProducer());
            pstmt.setInt(5, vehicle.getMilage());
            pstmt.setDouble(6, vehicle.getPreviousIndemnity());
            pstmt.setInt(7, vehicle.getId());
            int affectedRows = pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Vehicle> findAll() {
        String sql = "SELECT * FROM vehicles";
        List<Vehicle> vehicles = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                vehicles.add(createVehicleORM(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return vehicles;
    }

    @Override
    public Vehicle findById( int id ) {
        String sql = "SELECT * FROM VEHICLES WHERE id=?";
        Vehicle vehicle = null;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next())
                vehicle = createVehicleORM(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return vehicle;
    }

    private Vehicle createVehicleORM( ResultSet resultSet ) throws SQLException {
        Vehicle vehicle = null;
        vehicle = new Vehicle();
        vehicle.setId(resultSet.getInt("id"));
        vehicle.setPlateNumber(resultSet.getString("plate_number"));
        vehicle.setFirstRegistration(resultSet.getInt("first_registration"));
        vehicle.setPurchasePrise(resultSet.getInt("purchase_prise"));
        vehicle.setProducer(resultSet.getString("producer"));
        vehicle.setMilage(resultSet.getInt("milage"));
        vehicle.setPreviousIndemnity(resultSet.getInt("previous_indemnity"));
        return vehicle;
    }
}
