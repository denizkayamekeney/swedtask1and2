package task1.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import task1.dto.Vehicle;

import org.springframework.data.domain.Pageable;

@Repository
public interface VehiclesDao extends PagingAndSortingRepository<Vehicle, Integer> {
    Page<Vehicle> findAll( Pageable pageable );
}
