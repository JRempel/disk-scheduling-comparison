package exceptions;

public class InvalidGeneratorParameterException extends RuntimeException {
    private static final String messageFormat = "Invalid Generator Parameter %s from %s, %s";

    public InvalidGeneratorParameterException(String parameterName, Class clazz, String message) {
        super(String.format(messageFormat, parameterName, clazz.getName(), message));
    }
}
