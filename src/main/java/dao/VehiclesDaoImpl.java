package dao;

import dto.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiclesDaoImpl implements VehiclesDao {
    Connection connection;

    public VehiclesDaoImpl( Connection connection ) {
        this.connection = connection;
    }

    @Override
    public boolean insert( Vehicle vehicle ) {

        String SQL = "INSERT INTO VEHICLES(id, plate_number, first_registration, purchase_prise, producer, milage, previous_indemnity) "
                + "VALUES(?,?,?,?,?,?,?)";

        try (PreparedStatement pstmt = connection.prepareStatement(SQL)) {

            pstmt.setInt(1, vehicle.getId());
            pstmt.setString(2, vehicle.getPlate_number());
            pstmt.setInt(3, vehicle.getFirst_registration());
            pstmt.setDouble(4, vehicle.getPurchase_prise());
            pstmt.setString(5, vehicle.getProducer());
            pstmt.setInt(6, vehicle.getMilage());
            pstmt.setDouble(7, vehicle.getPrevious_indemnity());

            int affectedRows = pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
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

            pstmt.setString(1, vehicle.getPlate_number());
            pstmt.setInt(2, vehicle.getFirst_registration());
            pstmt.setDouble(3, vehicle.getPurchase_prise());
            pstmt.setString(4, vehicle.getProducer());
            pstmt.setInt(5, vehicle.getMilage());
            pstmt.setDouble(6, vehicle.getPrevious_indemnity());
            pstmt.setInt(7, vehicle.getId());

            int affectedRows = pstmt.executeUpdate();
//            System.out.println("inserted" + vehicle.getId());
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
                Vehicle vehicle = new Vehicle();
                vehicle.setId(resultSet.getInt("id"));
                vehicle.setPlate_number(resultSet.getString("plate_number"));
                vehicle.setFirst_registration(resultSet.getInt("first_registration"));
                vehicle.setPurchase_prise(resultSet.getInt("purchase_prise"));
                vehicle.setProducer(resultSet.getString("producer"));
                vehicle.setMilage(resultSet.getInt("milage"));
                vehicle.setPrevious_indemnity(resultSet.getInt("previous_indemnity"));
                vehicles.add(vehicle);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return vehicles;
    }
}
