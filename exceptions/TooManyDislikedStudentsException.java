package au.edu.rmit.projectmanager.exceptions;

public class TooManyDislikedStudentsException extends Exception {
    public TooManyDislikedStudentsException() {
        super("Trying to add too many disliked students.");
    }
}