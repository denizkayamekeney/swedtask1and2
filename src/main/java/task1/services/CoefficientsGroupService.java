package task1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task1.dao.CoefficientGroupDao;
import task1.dto.CoefficientGroup;

import java.util.List;
import java.util.Optional;

@Service
public class CoefficientsGroupService {

    @Autowired
    CoefficientGroupDao coefficientGroupDao;

    /**
     * Insert a single coefficientGroup into database
     */

    public CoefficientGroup save( CoefficientGroup coefficientGroup ) {
        return coefficientGroupDao.save(coefficientGroup);
    }

    /**
     * Delete a single coefficientGroup into database
     */

    public void deleteById( int id) {
        coefficientGroupDao.deleteById(id);
    }

    /**
     * Returns all data in the table
     */
    public List<CoefficientGroup> findAll() {
        return (List<CoefficientGroup>) coefficientGroupDao.findAll();
    }


    /**
     *   Returns the coefficientGroup if exist in database with a given id.
     */
    public Optional<CoefficientGroup> findById( int id ) {
        return coefficientGroupDao.findById(id);
    }

}
