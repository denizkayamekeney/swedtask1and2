package task1.services;

import task1.dao.VehiclesDao;
import task1.dto.Vehicle;
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

    public VehiclesService( VehiclesDao vehiclesDao,
                            VehicleCSVParser vehicleCSVParser,
                            LogHelper logHelper){
        this.vehiclesDao = vehiclesDao;
        this.vehicleCSVParser = vehicleCSVParser;
        this.logHelper = logHelper;
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

    public boolean save( Vehicle vehicle) throws SQLException{
        return vehiclesDao.insert(vehicle);
    }

    public List<Vehicle> findAll(){
        return vehiclesDao.findAll();
    }

    public Vehicle findById(int id){
        return vehiclesDao.findById(id);
    }


    public boolean loadVehicleFromCsvFile(String csvFileWithPath) throws IOException {
        List<Vehicle> list = vehicleCSVParser.parseData(csvFileWithPath);

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(logHelper.getErrorLogFileWithPath(), true))) {
            for (Vehicle vehicle : list) {
                try {
                    vehiclesDao.insert(vehicle);
                } catch (Exception exception) {
                    writer.append(logHelper.objectExceptionToString(exception, vehicle));
                }
            }
        }
        return true;
    }


}
