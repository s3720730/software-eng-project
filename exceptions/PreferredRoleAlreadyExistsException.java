package au.edu.rmit.projectmanager.exceptions;

public class PreferredRoleAlreadyExistsException extends Exception {
    public PreferredRoleAlreadyExistsException() {
        super("Preferred role already exists.");
    }
}