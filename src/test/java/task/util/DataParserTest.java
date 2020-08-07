package task.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import task1.FileAppException;
import task1.dto.CoeficientsData;
import task1.utils.DataParser;

import java.io.IOException;

@Disabled
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class DataParserTest {

    @Test
    public void withFullNodes(){
        CoeficientsData data = DataParser.loadDataFile("src/test/resources/withFullNodes.json");
        Assertions.assertTrue(data.getMake_coefficients().size() == 6);
        Assertions.assertTrue(data.getAvg_purchase_price().size() == 11);
        Assertions.assertTrue(data.getCoefficients().size() == 3);
    }

    @Test
    public void withWrongDoubleValue() throws IOException {
        Assertions.assertThrows(FileAppException.class,
                () -> DataParser.loadDataFile("src/test/resources/withWrongDoubleValue.json"),
                "It is expected to be thrown FileAppException");
    }

    @Test
    public void withAbsentNode() throws IOException {
        Assertions.assertThrows(FileAppException.class,
                () -> DataParser.loadDataFile("src/test/resources/withAbsentNode.json"),
                "It is expected to be thrown FileAppException");
    }

}
