package task1.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import task1.dto.Vehicle;

@Repository
public interface VehiclesDao extends PagingAndSortingRepository<Vehicle, Integer> {
}
