package services;

import dao.VehiclesDao;
import dto.Vehicle;
import task1.utils.Formulas;
import task1.utils.VehicleCSVParser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class VehiclesService {
    VehiclesDao vehiclesDao;

    public VehiclesService( VehiclesDao vehiclesDao ){
        this.vehiclesDao = vehiclesDao;
    }

    public boolean save( Vehicle vehicle) throws SQLException{
        return vehiclesDao.insert(vehicle);
    }

    public List<Vehicle> findAll(){
        return vehiclesDao.findAll();
    }

    public Vehicle calculateKasko(Vehicle vehicle){
        vehicle.setPrevious_indemnity(Formulas.getPurchasePrisePercentage(2020 - vehicle.getFirst_registration(),vehicle.getMilage()));
        return vehicle;
    }

    public boolean calculateAllKasko() throws IOException, SQLException {
        for (Vehicle vehicle : vehiclesDao.findAll()){
            vehiclesDao.update(calculateKasko(vehicle));
        }
        return true;
    }

    public boolean loadVehicleFromCsvFile(String csvFileWithPath) throws IOException, SQLException {
        List<Vehicle> list = VehicleCSVParser.parseData(csvFileWithPath);
        for (Vehicle vehicle : list) {
            vehiclesDao.insert(vehicle);
        }
        return true;
    }


}
