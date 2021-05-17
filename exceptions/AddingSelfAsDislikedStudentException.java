package au.edu.rmit.projectmanager.exceptions;

public class AddingSelfAsDislikedStudentException extends Exception {
    public AddingSelfAsDislikedStudentException() {
        super("Can't add self as disliked student");
    }
}
