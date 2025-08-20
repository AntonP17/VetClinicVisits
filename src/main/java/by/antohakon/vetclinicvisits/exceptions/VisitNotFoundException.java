package by.antohakon.vetclinicvisits.exceptions;

public class VisitNotFoundException extends RuntimeException {
    public VisitNotFoundException(String message) {
        super(message);
    }
}
