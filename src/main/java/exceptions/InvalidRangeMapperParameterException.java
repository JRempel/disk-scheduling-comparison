package exceptions;

public class InvalidRangeMapperParameterException extends RuntimeException {
    private static final String messageFormat = "Invalid Generator Parameter %s, %s";

    public InvalidRangeMapperParameterException(String parameterName, String message) {
        super(String.format(messageFormat, parameterName, message));
    }
}
