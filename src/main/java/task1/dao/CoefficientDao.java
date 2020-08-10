package task1.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task1.dto.Coefficient;

import java.util.List;


@Repository
public interface CoefficientDao extends JpaRepository<Coefficient, Integer> {
        List<Coefficient> findByGroupId( int groupId );
}
