package task1.dao;

import task1.dto.Vehicle;

import java.util.List;

public interface VehiclesDao {
    boolean insert( Vehicle vehicle);
    boolean update( Vehicle vehicle);
    List<Vehicle> findAll();
    Vehicle findById(int id);
}
