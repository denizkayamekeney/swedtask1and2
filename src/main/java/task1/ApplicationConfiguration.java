package task1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import task1.dto.CoeficientsData;
import task1.utils.DataParser;

@Configuration
public class ApplicationConfiguration {
    @Value("${data.coeficients.jsonfile}")
    String fileName;

    @Bean
    public CoeficientsData coeficientsData() {
        return DataParser.loadDataFile(fileName);
    }

}
