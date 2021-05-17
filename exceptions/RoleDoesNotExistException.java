package au.edu.rmit.projectmanager.exceptions;

public class RoleDoesNotExistException extends Exception {
    public RoleDoesNotExistException() {
        super("Role does not exist");
    }
}
