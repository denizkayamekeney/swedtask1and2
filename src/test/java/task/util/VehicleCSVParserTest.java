package task.util;

import org.junit.jupiter.api.*;
import task1.dao.VehicleHelper;
import task1.dto.Vehicle;
import task1.utils.Constants;
import task1.utils.LogHelper;
import task1.utils.VehicleCSVParser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VehicleCSVParserTest {
    LogHelper logHelper;
    VehicleCSVParser vehicleCSVParser;
    final String inputFileName = "testinput.dat";

    Path inputFilePath;
    Path errorFilePath;

    @BeforeEach
    public void deleteTestFile(){
        try {
            Files.deleteIfExists(inputFilePath);
            Files.deleteIfExists(errorFilePath);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    @BeforeAll
    public void initDataBase() throws SQLException {
        inputFilePath = Paths.get(inputFileName);
        errorFilePath = Paths.get(Constants.ERROR_FILE_PATH_TEST);

        logHelper = new LogHelper(Constants.ERROR_FILE_PATH_TEST);
        vehicleCSVParser = new VehicleCSVParser(logHelper);
    }

    @Test
    public void oneRecordCSVCompareVehicleTest() throws IOException {
        Vehicle vehicle = VehicleHelper.createRandomVehicle();

        StringBuilder sb = new StringBuilder("plate_number,first_registration,purchase_price,producer,mileage,previous_indemnity\n")
                .append(getVehicleAsCSVLine(vehicle))
                .append("\n");
        writeToFile(sb.toString());

        List<Vehicle> vehicles = vehicleCSVParser.parseData(inputFileName);
        Assertions.assertTrue(vehicles.size() == 1,"It is expected to be the size equals 10!");
        Assertions.assertEquals(vehicle,vehicles.get(0),"It is expected to be the same vehicle class");
    }
    @Test
    public void tenRecordAllCorrectParse() throws IOException {
        List<Vehicle> vehicles = vehicleCSVParser.parseData("src/test/resources/ten_record_correct_parse.csv");
        Assertions.assertTrue(vehicles.size() == 10,"It is expected to be the size equals 10!");
    }

    @Test
    public void WrongNumberParseTest() throws IOException {
        List<Vehicle> vehicles = vehicleCSVParser.parseData("src/test/resources/wrong_number_parse.csv");
        Assertions.assertTrue(vehicles.size() == 0,"It is expected to be the size equals 0!");
        String errorText = Files.readString(errorFilePath);
        Assertions.assertTrue(errorText.contains("java.lang.NumberFormatException"),"Number format exception is expected!");
    }

    @Test
    public void EmptyPlateNumberTestParseTest() throws IOException {
        List<Vehicle> vehicles = vehicleCSVParser.parseData("src/test/resources/empty_plate_number_parse.csv");
        Assertions.assertTrue(vehicles.size() == 1,"It is expected to be the size equals 1!");
        Assertions.assertTrue(vehicles.get(0).getPlateNumber().isEmpty(),"It should be empty plate number!");
    }

    @Test
    public void oneLessThenExpectedColumnNumberValueParserTest() throws IOException {
        List<Vehicle> vehicles = vehicleCSVParser.parseData("src/test/resources/one_less_column_data_parse.csv");
        Assertions.assertTrue(vehicles.size() == 0,"It is expected to be the size equals 0!");
        String errorText = Files.readString(errorFilePath);
        Assertions.assertTrue(errorText.contains("IllegalArgumentException"),"IllegalArgumentException is expected!");
    }

    @Test
    public void writeToCSV_Test() throws IOException {
        Vehicle vehicle = VehicleHelper.createRandomVehicle();
        vehicle.setCascoWithIndemnity(100);
        vehicle.setCascoWithoutIndemnity(200);

        List<Vehicle> vehicles = List.of(vehicle);
        vehicleCSVParser.writeToCSV(vehicles,"MyFile.csv");
        var vehicles2 = vehicleCSVParser.parseDataForTest("MyFile.csv");
        Assertions.assertEquals(vehicles2.get(0),vehicle,"The vehicle objects needs to be equal!!");
    }


    public void writeToFile(String text){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(inputFileName, true))) {
            writer.append(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getVehicleAsCSVLine(Vehicle vehicle){
        return vehicle.getId() + "," +vehicle.getPlateNumber() + "," + vehicle.getFirstRegistration()+","
                + vehicle.getPurchasePrise() + "," + vehicle.getProducer() + "," + vehicle.getMilage() + "," +
                vehicle.getPreviousIndemnity();
    }
}
