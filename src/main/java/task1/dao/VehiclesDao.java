package task1.dao;

import task1.dto.Vehicle;

import java.sql.SQLException;
import java.util.List;

public interface VehiclesDao {
    boolean insert( Vehicle vehicle) throws SQLException;
    boolean update( Vehicle vehicle) throws SQLException;
    List<Vehicle> findAll();
    Vehicle findById(int id);
}
