package task1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task1.FileAppException;
import task1.dao.VehiclesDao;
import task1.dto.CalculationCriterias;
import task1.dto.Vehicle;
import task1.utils.CascoCalculator;
import task1.utils.LogHelper;
import task1.utils.VehicleCSVParser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import java.util.Optional;

@Service
public class VehiclesService {

    VehiclesDao vehiclesDao;

    VehicleCSVParser vehicleCSVParser;

    LogHelper logHelper;

    CascoCalculator cascoCalculator;

    @Autowired
    public VehiclesService( VehiclesDao vehiclesDao,
                            VehicleCSVParser vehicleCSVParser,
                            LogHelper logHelper,
                            CascoCalculator cascoCalculator ) {

        this.vehiclesDao = vehiclesDao;
        this.vehicleCSVParser = vehicleCSVParser;
        this.logHelper = logHelper;
        this.cascoCalculator = cascoCalculator;
    }

    public CascoCalculator getCascoCalculator() {
        return cascoCalculator;
    }

    public void setCascoCalculator( CascoCalculator cascoCalculator ) {
        this.cascoCalculator = cascoCalculator;
    }

    public LogHelper getLogHelper() {
        return logHelper;
    }

    public void setLogHelper( LogHelper logHelper ) {
        this.logHelper = logHelper;
    }

    public VehiclesDao getVehiclesDao() {
        return vehiclesDao;
    }

    public void setVehiclesDao( VehiclesDao vehiclesDao ) {
        this.vehiclesDao = vehiclesDao;
    }

    public VehicleCSVParser getVehicleCSVParser() {
        return vehicleCSVParser;
    }

    public void setVehicleCSVParser( VehicleCSVParser vehicleCSVParser ) {
        this.vehicleCSVParser = vehicleCSVParser;
    }

    /**
     * Insert a single vehicle into database
     */

    public Vehicle save( Vehicle vehicle ) {
        return vehiclesDao.save(vehicle);
    }

    /**
     * Returns all data in the table
     */
    public List<Vehicle> findAll() {
        return (List<Vehicle>) vehiclesDao.findAll();
    }

    /**
     *   Returns the vehicle if exist in database with a given id.
     */
    public Optional<Vehicle> findById( int id ) {
        return vehiclesDao.findById(id);
    }


    /**
     * It loads the vehicles in a given csv file into the memory.
     */
    public List<Vehicle> loadVehicleFromCsvFile( String csvFileWithPath ) {
        List<Vehicle> vehicles = vehicleCSVParser.parseData(csvFileWithPath);
        return vehicles;
    }

    /**
     * It inserts all coming vehicles to the database!.
     */
    public boolean insertVehicles( List<Vehicle> vehicles ) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logHelper.getErrorLogFileWithPath(), true))) {
            for (Vehicle vehicle : vehicles) {
                try {
                    vehiclesDao.save(vehicle);
                } catch (Exception exception) {
                    writer.append(logHelper.objectExceptionToString(exception, vehicle));
                }
            }
        } catch (Exception exception) {
            throw new FileAppException(String.format("It occured and error while reading %s file!",
                    logHelper.getErrorLogFileWithPath()), exception);
        }
        return true;
    }

    /**
     * Updates all coming vehicles to the database!.
     */
    public void saveVehicles( List<Vehicle> vehicles ) {
        vehiclesDao.saveAll(vehicles);
    }

    /**
     * Calculates the vehicles casco according to given criteria!.
     */
    public boolean calculateVehiclesCasco( List<Vehicle> vehicles ) {
        for (Vehicle vehicle : vehicles) {
            if (cascoCalculator.getCriterias().contains(CalculationCriterias.PreviousIndemnity)) {
                vehicle.setCascoWithIndemnity(cascoCalculator.getAnnualFee(vehicle));
            } else {
                vehicle.setCascoWithoutIndemnity(cascoCalculator.getAnnualFee(vehicle));
            }
        }
        return true;
    }

    /**
     * Saves the vehicles to the given file in csv format.
     */
    public boolean saveVehiclesToCSVFile( List<Vehicle> vehicles, String fileName ) {
        return vehicleCSVParser.writeToCSV(vehicles, fileName);
    }
}
