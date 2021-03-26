package exceptions;

public class IllegalOperationException extends Exception{
    public IllegalOperationException() {
    }

    public IllegalOperationException(String message) {
        super(message);
    }
}
