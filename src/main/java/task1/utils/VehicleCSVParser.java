package task1.utils;

import task1.dto.Vehicle;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleCSVParser {

    enum VehicleHeaders {
        id, plate_number, first_registration, purchase_prise,
        producer, milage, previous_indemnity
    }

    LogHelper logHelper;

    public VehicleCSVParser(LogHelper logHelper) {
        this.logHelper = logHelper;
    }

    public List<Vehicle> parseData( String csvFileWithPath ) throws IOException {
        Reader in = new FileReader(csvFileWithPath);
        List<Vehicle> list = new ArrayList<>();

        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withFirstRecordAsHeader().withHeader(VehicleHeaders.class).parse(in);

        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(logHelper.getErrorLogFileWithPath(), false))){
            for (CSVRecord record : records) {
                try {
                    Vehicle vehicle = new Vehicle(
                            Integer.parseInt(record.get(VehicleHeaders.id)), // id
                            record.get(VehicleHeaders.plate_number),  // plate_number
                            Integer.parseInt(record.get(VehicleHeaders.first_registration)),  // first_registration
                            Integer.parseInt(record.get(VehicleHeaders.purchase_prise)),  // first_registration
                            record.get(VehicleHeaders.producer),  // first_registration
                            Integer.parseInt(record.get(VehicleHeaders.milage)),  // first_registration
                            Double.parseDouble(record.get(VehicleHeaders.previous_indemnity))
                    );
                    list.add(vehicle);
                } catch (Exception exception) {
                    writer.append(logHelper.objectExceptionToString(exception, record));
                }
            }
        }
        return list;
    }


}
