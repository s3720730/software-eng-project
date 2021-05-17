package au.edu.rmit.projectmanager.exceptions;

public class InvalidConstraintWeightException extends Exception {
    public InvalidConstraintWeightException() {
        super("The value of the constraint weight is invalid");
    }
}
