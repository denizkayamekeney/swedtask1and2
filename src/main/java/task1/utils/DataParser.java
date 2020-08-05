package task1.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import task1.dto.CoeficientsData;
import task1.dto.DataItem;

import java.io.File;
import java.io.IOException;

public class DataParser {
    static ObjectMapper objectMapper = new ObjectMapper();
    static CoeficientsData data;

    public static CoeficientsData loadDataFile(String fileWithPath) throws IOException {
        DataItem dataItem = objectMapper.readValue(new File(fileWithPath), DataItem.class);
        data =  dataItem.getData();
        return data;
    }
    public static CoeficientsData getData() {
        return data;
    }
}
