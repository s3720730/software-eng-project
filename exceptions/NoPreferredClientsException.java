package au.edu.rmit.projectmanager.exceptions;

public class NoPreferredClientsException extends Exception {
    public NoPreferredClientsException() {
        super("No Preferred Clients found.");
    }
}
