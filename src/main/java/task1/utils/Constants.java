package task1.utils;

public interface Constants {
  // Path of data json file that will be input to the calculation
  String JSON_DATA_FILE_PATH = "src/main/resources/data.json";

  // Path of vehicle csv file.
  String CSV_DATA_FILE_PATH = "src/main/resources/vehicles.csv";

  // All parsing errors results are written this file/
  String ERROR_FILE_PATH = "csvErrors.dat";


  String ERROR_FILE_PATH_TEST = "csvErrorsTest.dat";

  // It is DDL file for creating the Vehicles table
  String DDL_FILE_PATH = "src/main/resources/database.sql";

  // Database credentials
  String USER = "sa";
  String PASS = "";

}


