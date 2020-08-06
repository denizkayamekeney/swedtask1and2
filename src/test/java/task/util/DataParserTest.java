package task.util;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import task1.dto.CoeficientsData;
import task1.utils.DataParser;

import java.io.IOException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DataParserTest {

    @Test
    public void withFullNodes() throws IOException {
        CoeficientsData data = DataParser.loadDataFile("src/test/resources/withFullNodes.json");
        Assertions.assertTrue(data.getMake_coefficients().size() == 6);
        Assertions.assertTrue(data.getAvg_purchase_price().size() == 11);
        Assertions.assertTrue(data.getCoefficients().size() == 3);
    }

    @Test
    public void withWrongDoubleValue() throws IOException {
        Assertions.assertThrows(JsonMappingException.class,
                () -> DataParser.loadDataFile("src/test/resources/withWrongDoubleValue.json"),
                "It is expected to be thrown JsonMappingException");
    }

    @Test
    public void withAbsentNode() throws IOException {
        Assertions.assertThrows(JsonMappingException.class,
                () -> DataParser.loadDataFile("src/test/resources/withAbsentNode.json"),
                "It is expected to be thrown UnrecognizedPropertyException");
    }

}
