package task1.services;

import task1.dao.VehiclesDao;
import task1.dto.CalculationCriterias;
import task1.dto.Vehicle;
import task1.utils.CascoCalculator;
import task1.utils.LogHelper;
import task1.utils.VehicleCSVParser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class VehiclesService {

    VehiclesDao vehiclesDao;

    VehicleCSVParser vehicleCSVParser;

    LogHelper logHelper;

    CascoCalculator cascoCalculator;

    // Constructor
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
     *   Insert a single vehicle into database
     */

    public boolean insert( Vehicle vehicle ) throws SQLException {
        return vehiclesDao.insert(vehicle);
    }

    /**
     *   Updates single vehicle in database
     */
    public boolean update( Vehicle vehicle ) throws SQLException {
        return vehiclesDao.update(vehicle);
    }

    /**
     *   Returns all data in the table
     */
    public List<Vehicle> findAll() {
        return vehiclesDao.findAll();
    }

    /**
     *   Returns the vehicle if exist in database with a given id.
     */
    public Vehicle findById( int id ) {
        return vehiclesDao.findById(id);
    }


    /**
     *   It loads the vehicles in a given csv file into the memory.
     */
    public List<Vehicle> loadVehicleFromCsvFile( String csvFileWithPath ) throws IOException {
        List<Vehicle> vehicles = vehicleCSVParser.parseData(csvFileWithPath);

        return vehicles;
    }

    /**
     *   It inserts all coming vehicles to the database!.
     */
    public boolean insertVehicles( List<Vehicle> vehicles ) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logHelper.getErrorLogFileWithPath(), true))) {
            for (Vehicle vehicle : vehicles) {
                try {
                    vehiclesDao.insert(vehicle);
                } catch (Exception exception) {
                    writer.append(logHelper.objectExceptionToString(exception, vehicle));
                }
            }
        }
        return true;
    }

    /**
     *   Updates all coming vehicles to the database!.
     */
    public void updateVehicles( List<Vehicle> vehicles ) throws IOException, SQLException {
        for (Vehicle vehicle : vehicles) {
            this.update(vehicle);
        }
    }

    /**
     *   Calculates the vehicles casco according to given criteria!.
     */
    public boolean calculateVehiclesCasco( List<Vehicle> vehicles ) throws IOException {
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
     *   Saves the vehicles to the given file in csv format.
     */
    public boolean saveVehiclesToCSVFile( List<Vehicle> vehicles, String fileName ) throws IOException {
        return vehicleCSVParser.writeToCSV(vehicles, fileName);
    }
}
