package by.antohakon.vetclinicvisits.exceptions;

public class KafkaSendMessageException extends RuntimeException {
    public KafkaSendMessageException(String message) {
        super(message);
    }
}
