package task1;

public class AppException extends RuntimeException {
    public AppException(String msg, Throwable cause) {
        super(msg, cause);
    }
}