package exceptions;
public class EmptyQueueException extends RuntimeException {
    private static final String MESSAGE_FORMAT = "Empty Queue exception from %s";

    public EmptyQueueException(Class clazz) {
        super(String.format(MESSAGE_FORMAT, clazz.getName()));
    }
}
