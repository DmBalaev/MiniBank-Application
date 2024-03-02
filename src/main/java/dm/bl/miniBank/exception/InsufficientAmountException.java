package dm.bl.miniBank.exception;

public class InsufficientAmountException extends RuntimeException{
    public InsufficientAmountException(String message) {
        super(message);
    }
}
