package au.edu.rmit.projectmanager.exceptions;

public class ProjectDoesNotExistException extends Exception {

    public ProjectDoesNotExistException() {
        super("Project does not exist");
    }
}
