package task1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task1.dao.CoefficientDao;
import task1.dto.Coefficient;

import java.util.List;
import java.util.Optional;

@Service
public class CoefficientService {
    @Autowired
    CoefficientDao coefficientDao;

    /**
     * Insert a single coefficient into database
     */

    public Coefficient save( Coefficient coefficient ) {
        return coefficientDao.save(coefficient);
    }

    /**
     * Delete a single coefficient into database
     */

    public void deleteById( int id) {
        coefficientDao.deleteById(id);
    }

    /**
     * Returns all data in the table
     */
    public List<Coefficient> findAll() {
        return (List<Coefficient>) coefficientDao.findAll();
    }

    /**
     * Returns all data in that group
     */
    public List<Coefficient> findByGroupId(int groupId) {
        return (List<Coefficient>) coefficientDao.findByGroupId(groupId);
    }


    /**
     *   Returns the coefficient if exist in database with a given id.
     */
    public Optional<Coefficient> findById( int id ) {
        return coefficientDao.findById(id);
    }

}

