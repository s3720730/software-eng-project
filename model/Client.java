package au.edu.rmit.projectmanager.model;

import au.edu.rmit.projectmanager.exceptions.ProjectAlreadyExistsException;
import au.edu.rmit.projectmanager.exceptions.ProjectDoesNotExistException;

import java.util.ArrayList;

public class Client extends User {

    private static final long serialVersionUID = 6529685098267757670L;

    private ArrayList<Project> projects;

    public Client(String name, String username, String password) {
        super(name, username, password);
        projects = new ArrayList<>();
    }

    /**
     * Create a new project
     *
     * @param title Project title
     * @throws ProjectAlreadyExistsException The project already exists in the system
     */
    public boolean createProject(String title) throws ProjectAlreadyExistsException {
        boolean valid = true;
        for (Project p : projects) {
            if (p.getTitle().toLowerCase().equals(title.toLowerCase())) {
                valid = false;
                throw new ProjectAlreadyExistsException();
            }
        }

        Project p = new Project(this, title);
        projects.add(p);
        return valid;
    }

    /**
     * Delete a project
     *
     * @param title Title of the project to be deleted
     * @throws ProjectDoesNotExistException The project does not exist in the system
     */
    public void deleteProject(String title) throws ProjectDoesNotExistException {
        for (Project p : projects) {

            if (p.getTitle().toLowerCase().equals(title.toLowerCase())) {
                projects.remove(p);
                return;
            }
        }
        throw new ProjectDoesNotExistException();
    }

    /**
     * Return all the client's projects
     * @return All client's projects
     */
    public ArrayList<Project> getProjects() {
        return projects;
    }

    /**
     * Return a specified project
     *
     * @param title Name of the specified project
     * @return Reference to the project
     * @throws ProjectDoesNotExistException The project does not exist in the system
     */
    public Project getProject(String title) throws ProjectDoesNotExistException {

        for (Project p : projects) {
            if (p.getTitle().equals(title)) {
                return p;
            }

        }
        throw new ProjectDoesNotExistException();
    }

    public void addProject(Project project) {
        this.projects.add(project);
    }

    /**
     * Set the roles for a specific project
     * @param title The project's title
     * @param roles The roles
     * @throws ProjectDoesNotExistException The project does not exist in the system
     */
    public void setProjectRoles(String title, ArrayList<Role> roles) throws ProjectDoesNotExistException {

        for (Project p : projects) {
            if (p.getTitle().equals(title)) {
                p.setRoles(roles);
                return;
            }

        }
        throw new ProjectDoesNotExistException();
    }


    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "[Name: " + name + "][Username: " + username + "]";
    }
}