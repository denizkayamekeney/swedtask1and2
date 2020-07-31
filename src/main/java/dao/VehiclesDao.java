package dao;

import dto.Vehicle;

import java.sql.SQLException;
import java.util.List;

public interface VehiclesDao {
    boolean insert( Vehicle vehicle) throws SQLException;
    boolean update( Vehicle vehicle) throws SQLException;
    List<Vehicle> findAll();
}
