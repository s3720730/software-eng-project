package au.edu.rmit.projectmanager.exceptions;

public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException() {
        super("Invalid password.");
    }
}