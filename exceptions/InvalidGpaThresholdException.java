package au.edu.rmit.projectmanager.exceptions;

public class InvalidGpaThresholdException extends Exception {
    public InvalidGpaThresholdException() {
        super("The value of the GPA threshold is invalid");
    }
}
