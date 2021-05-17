package au.edu.rmit.projectmanager.exceptions;

public class PreferredClientAlreadyExistsException extends Exception {
    public PreferredClientAlreadyExistsException() {
        super("Preferred client already exists.");
    }
}