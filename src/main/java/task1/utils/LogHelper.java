package task1.utils;

final public class LogHelper{

    String errorLogFileWithPath;

    public LogHelper( String errorLogFileWithPath ) {
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
    }

}
