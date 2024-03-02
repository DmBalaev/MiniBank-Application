package dm.bl.miniBank.exception;

public class ApiException extends RuntimeException{
    public ApiException(String message) {
        super(message);
    }
}
