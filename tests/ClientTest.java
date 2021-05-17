package au.edu.rmit.projectmanager.tests;

import au.edu.rmit.projectmanager.exceptions.ProjectAlreadyExistsException;
import au.edu.rmit.projectmanager.exceptions.ProjectDoesNotExistException;
import au.edu.rmit.projectmanager.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClientTest {

    Client c = new Client("Sam", "s12345", "password");
    ArrayList<String> frameworks = new ArrayList<>();
    ArrayList<String> languages = new ArrayList<>();

    @BeforeEach
    void setUp() {

        frameworks.add("Django");
        languages.add("Java");
        languages.add("C++");

    }

    @Test
    void createProject() {

        //checking no projects already exist
        assertEquals(0, c.getProjects().size());

        //creating project
        try {
            c.createProject("project1");
        } catch (ProjectAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }

        //testing information is correct
        assertEquals(c.getProjects().size(), 1);
        assertEquals(c.getProjects().get(0).getTitle(), "project1");


    }

    @Test
    void createProjectAlreadyCreated() {

        //checking no projects already exist
        assertEquals(0, c.getProjects().size());

        try {
            c.createProject("project1");
        } catch (ProjectAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }

        //testing exception is thrown when trying to make a the same project
        assertThrows(ProjectAlreadyExistsException.class, () -> c.createProject("project1"));

    }

    @Test
    void deleteProject() {

        //checking no projects already exist
        assertEquals(c.getProjects().size(), 0);
        String title = "project";

        try {
            c.createProject(title);
        } catch (ProjectAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(c.getProjects().size(), 1);

        //deleting the project
        try {
            c.deleteProject(title);
        } catch (ProjectDoesNotExistException e) {
            System.out.println(e.getMessage());
        }

        //testing the project gets deleted correctly
        assertEquals(c.getProjects().size(), 0);

    }

    @Test
    void deleteAbsentProject() {

        //checking no projects already exist
        assertEquals(c.getProjects().size(), 0);

        //testing the 'project does not exist' function works correctly
        assertThrows(ProjectDoesNotExistException.class, () -> c.deleteProject("invalid project"));
    }
}