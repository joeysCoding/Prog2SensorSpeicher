package exceptions;

public class NoSensorDataException extends Exception {

    public NoSensorDataException() {
    }

    public NoSensorDataException(String message) {
        super(message);
    }
}
