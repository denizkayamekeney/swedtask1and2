package task1.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
final public class LogHelper{

    String errorLogFileWithPath;

    @Autowired
    public LogHelper( @Value("${csvErrorFile}") String errorLogFileWithPath ) {
        this.errorLogFileWithPath = errorLogFileWithPath;
    }

    public String getErrorLogFileWithPath() {
        return errorLogFileWithPath;
    }

    public void setErrorLogFileWithPath( String errorLogFileWithPath ) {
        this.errorLogFileWithPath = errorLogFileWithPath;
    }

    public String objectExceptionToString( Exception exception, Object object) {
        return new StringBuilder("Exception Class Name : ")
                .append(exception.getClass().getName() )
                .append("\nMessage : ")
                .append(exception.getMessage())
                .append("\nValues : ")
                .append(object.toString())
                .append("\n")
                .append("-".repeat(60))
                .append("\n")
                .toString();
        //TODO : It needs to make detail of error.
    }

}
