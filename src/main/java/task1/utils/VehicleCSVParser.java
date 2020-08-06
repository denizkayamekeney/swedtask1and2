package task1.utils;

import org.apache.commons.csv.CSVPrinter;
import task1.FileAppException;
import task1.dto.Vehicle;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class VehicleCSVParser {
    // The headers for the parsing original Vehicle.json file.
    public enum VehicleHeaders {
        id, plate_number, first_registration, purchase_prise,
        producer, milage, previous_indemnity
    }

    /**
     * The headers for test for purposes. casco_without_indemnity, casco_with_indemnity
     *  is added to original header.
    */
    public enum VehicleTestHeaders {
        id, plate_number, first_registration, purchase_prise,
        producer, milage, previous_indemnity, casco_without_indemnity, casco_with_indemnity
    }

    LogHelper logHelper;

    public VehicleCSVParser( LogHelper logHelper ) {
        this.logHelper = logHelper;
    }

    public LogHelper getLogHelper() {
        return logHelper;
    }

    public void setLogHelper( LogHelper logHelper ) {
        this.logHelper = logHelper;
    }

    /**
     * It parses the original csv data file into vehicle object.
     *  If a parse error happens, it appends to error log file.
     */

    public List<Vehicle> parseData( String csvFileWithPath ){

        List<Vehicle> list = new ArrayList<>();

        try (  Reader in = new FileReader(csvFileWithPath);
               BufferedWriter writer = new BufferedWriter(new FileWriter(logHelper.getErrorLogFileWithPath(), false))) {

            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader().withHeader(VehicleHeaders.class).parse(in);
            for (CSVRecord record : records) {
                try {
                    Vehicle vehicle = new Vehicle(
                            Integer.parseInt(record.get(VehicleHeaders.id)), // id
                            record.get(VehicleHeaders.plate_number),  // plate_number
                            Integer.parseInt(record.get(VehicleHeaders.first_registration)),  // first_registration
                            Double.parseDouble(record.get(VehicleHeaders.purchase_prise)),  // first_registration
                            record.get(VehicleHeaders.producer),  // first_registration
                            Integer.parseInt(record.get(VehicleHeaders.milage)),  // first_registration
                            Double.parseDouble(record.get(VehicleHeaders.previous_indemnity))
                    );
                    list.add(vehicle);
                } catch (Exception exception) {
                    writer.append(logHelper.objectExceptionToString(exception, record));
                }
            }
        } catch (Exception exception){
            throw new FileAppException(String.format("It occured an error while opening the file!",
                    logHelper.getErrorLogFileWithPath()), exception);
        }
        return list;
    }


    /**
     * It writes the vehicles list into a file in CSV format with the following header.
     */
    public boolean writeToCSV( List<Vehicle> vehicles, String fileName ) {
        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName));
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                        .withHeader("id", "plate_number", "first_registration", "purchase_prise",
                                "producer", "milage", "previous_indemnity",
                                "casco_without_indemnity", "casco_with_indemnity"
                        ));
        ) {
            for (Vehicle vehicle : vehicles) {
                csvPrinter.printRecord(
                        vehicle.getId(),
                        vehicle.getPlateNumber(),
                        vehicle.getFirstRegistration(),
                        vehicle.getPurchasePrise(),
                        vehicle.getProducer(),
                        vehicle.getMilage(),
                        vehicle.getPreviousIndemnity(),
                        vehicle.getCascoWithoutIndemnity(),
                        vehicle.getCascoWithIndemnity()
                );
            }
            csvPrinter.flush();
        } catch (IOException exception) {
            throw new FileAppException(String.format("It occured an error while writing %s file!",
                    fileName), exception);
        }
        return true;
    }

}
