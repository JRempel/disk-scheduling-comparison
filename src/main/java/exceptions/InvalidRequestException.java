package exceptions;

public class InvalidRequestException extends RuntimeException {
    private static final String INVALID_REQUEST_MESSAGE= "Invalid cylinder request %s from %s scheduler.";

    public InvalidRequestException(int cylinder, Class clazz) {
        super(String.format(INVALID_REQUEST_MESSAGE, cylinder, clazz.getName()));
    }
}
