package au.edu.rmit.projectmanager.exceptions;

public class ProjectAlreadyExistsException extends Throwable {

    public ProjectAlreadyExistsException() {
        super("Project Already Exists");
    }
}
