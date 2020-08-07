package task1.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import task1.FileAppException;
import task1.dto.CoeficientsData;
import task1.dto.DataItem;

import java.io.File;

public class DataParser {
    static ObjectMapper objectMapper = new ObjectMapper();
    static CoeficientsData data;

    /**
     *   It parses the JSON data file onto object!
     */
    public static CoeficientsData loadDataFile(String fileWithPath) {
        CoeficientsData coeficientsData;
        try {
            DataItem dataItem = objectMapper.readValue(new File(fileWithPath), DataItem.class);
            data = dataItem.getData();
        } catch (Exception exception){
            throw new FileAppException(String.format("It occured and error while reading and loading %s file!",
                    fileWithPath), exception);
        }
        return data;
    }
    public static CoeficientsData getData() {
        return data;
    }
}
