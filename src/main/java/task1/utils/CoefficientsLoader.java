package task1.utils;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Component;
import task1.dao.CoefficientDao;
import task1.dao.CoefficientGroupDao;
import task1.dto.Coefficient;
import task1.dto.CoefficientGroup;
import task1.dto.CoeficientsData;
import task1.services.VehiclesService;

import java.util.Map;

@Component
@Profile("!test")
public class CoefficientsLoader implements ApplicationRunner{

    @Autowired
    private CoeficientsData coeficientsData;

    @Autowired
    private CoefficientDao coefficientDao;

    @Autowired
    private CoefficientGroupDao coefficientGroupDao;

    public void run( ApplicationArguments args) {

        // Adding Coefficients
        CoefficientGroup coefficientGroup = coefficientGroupDao.save(
                new CoefficientGroup("coefficients","Coefficients","Double")
        );
        for (Map.Entry<String,Double> entry: coeficientsData.getCoefficients().entrySet()) {
            Coefficient coefficient = new Coefficient(entry.getKey(),
                    entry.getKey(),
                    entry.getValue().toString(),
                    coefficientGroup.getId());
            coefficientDao.save(coefficient);
        }

        // Adding Make Coefficients
        coefficientGroup = coefficientGroupDao.save(
                new CoefficientGroup("make_coefficients","make_coefficients","Double"));
        for (Map.Entry<String,Double> entry: coeficientsData.getMake_coefficients().entrySet()) {
            Coefficient coefficient = new Coefficient(entry.getKey(),entry.getKey(),entry.getValue().toString(),coefficientGroup.getId());
            coefficientDao.save(coefficient);
        }

        // Adding avg_purchase_price
        coefficientGroup = coefficientGroupDao.save(new CoefficientGroup("avg_purchase_price","avg_purchase_price","Double"));
        for (Map.Entry<String,Double> entry: coeficientsData.getAvg_purchase_price().entrySet()) {
            Coefficient coefficient = new Coefficient(entry.getKey(),entry.getKey(),entry.getValue().toString(),coefficientGroup.getId());
            coefficientDao.save(coefficient);
        }

    }
}


