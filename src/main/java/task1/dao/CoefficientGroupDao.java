package task1.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task1.dto.CoefficientGroup;

@Repository
public interface CoefficientGroupDao extends JpaRepository<CoefficientGroup,Integer>  {
}
