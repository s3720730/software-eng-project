package au.edu.rmit.projectmanager.exceptions;


public class UserDoesNotExistException extends Exception {

    public UserDoesNotExistException() {
        super("No user with this username exists.");
    }

}
