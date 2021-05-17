package au.edu.rmit.projectmanager.exceptions;

public class DislikedStudentAlreadyExistsException extends Exception {
    public DislikedStudentAlreadyExistsException() {
        super("Disliked student already exists.");
    }
}
