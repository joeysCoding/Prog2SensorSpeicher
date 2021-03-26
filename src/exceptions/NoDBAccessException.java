package exceptions;

public class NoDBAccessException extends Exception{
    public NoDBAccessException() {
    }

    public NoDBAccessException(String message) {
        super(message);
    }
}
