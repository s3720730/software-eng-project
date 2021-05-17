package au.edu.rmit.projectmanager.exceptions;

public class TooManyPreferredClientsException extends Exception {
    public TooManyPreferredClientsException() {
        super("Trying to add too many preferred clients.");
    }
}