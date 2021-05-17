package au.edu.rmit.projectmanager.exceptions;

public class TeamAlreadyFullException extends Exception {
    public TeamAlreadyFullException() {
        super("The team is already full.");
    }
}
