package au.edu.rmit.projectmanager.exceptions;

public class TooManyPreferredRolesException extends Exception {
    public TooManyPreferredRolesException() {
        super("Trying to add too many preferred roles.");
    }
}
