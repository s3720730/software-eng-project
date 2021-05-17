package au.edu.rmit.projectmanager.exceptions;

public class GpaOutOfBoundsException extends Exception {
    public GpaOutOfBoundsException() {
        super("The value of the GPA  is invalid");
    }
}
