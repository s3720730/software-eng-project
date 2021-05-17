package au.edu.rmit.projectmanager.tests;

import au.edu.rmit.projectmanager.exceptions.TooManyPreferredRolesException;
import au.edu.rmit.projectmanager.exceptions.*;
import au.edu.rmit.projectmanager.model.Client;
import au.edu.rmit.projectmanager.model.Role;
import au.edu.rmit.projectmanager.model.Student;
import au.edu.rmit.projectmanager.model.Types;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    Student student;

    @BeforeEach
    public void setUp() {
        student = new Student("Sam", "s123", "password");
    }

    @Test
    public void setGpa() {
        double initalGpa = 0.0;
        double gpa = 3.5;

        //checking start parameters
        assertEquals(student.getGpa(), initalGpa);

        //setting new gpa
        try {
            student.setGpa(gpa);
        } catch (GpaOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }

        //checking final parameters
        assertEquals(student.getGpa(), gpa);
    }

    @Test
    public void setGpaOutOfRange() {
        double lowerBoundGpa = 0.0;
        double higherBoundGpa = 5.0;

        //setting gpa too high and too low
        assertThrows(GpaOutOfBoundsException.class, () -> student.setGpa(lowerBoundGpa));
        assertThrows(GpaOutOfBoundsException.class, () -> student.setGpa(higherBoundGpa));
    }

    @Test
    public void updateProfile() {
        double initialGpa = 0.0;
        int initialExperience = 0;
        double gpa = 3.5;
        int experience = 2;

        //checking start parameters
        assertEquals(initialGpa, student.getGpa());
        assertEquals(null, student.getGender());
        assertEquals(initialExperience, student.getYearsOfExperience());

        //updating student
        try {
            student.updateProfile(Types.Gender.Female, gpa, experience);
        } catch (GpaOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }

        //checking information is updated
        assertEquals(gpa, student.getGpa());
        assertEquals(Types.Gender.Female, student.getGender());
        assertEquals(experience, student.getYearsOfExperience());
    }

    @Test
    public void updateProfileGpaOutOfRange() {
        double invalidGpa = 5.5;
        int experience = 2;

        //updating student with invalid gpa
        assertThrows(GpaOutOfBoundsException.class, () -> student.updateProfile(Types.Gender.Female, invalidGpa, experience));
    }

    @Test
    public void addPreferredClient() {

        Client c1 = new Client("Matt", "c123", "password");
        Client c2 = new Client("Sally", "c124", "password");

        //checking no clients already exist
        assertEquals(0, student.getPreferredClients().size());

        //adding preferred client
        try {
            student.addPreferredClient(c1);
        } catch (Exception e) {}

        //checking client was added
        assertEquals(1, student.getPreferredClients().size());
        assertEquals(c1, student.getPreferredClients().get(0));

        //adding another preferred client
        try {
            student.addPreferredClient(c2);
        } catch (Exception e) {}

        //checking client was added
        assertEquals(2, student.getPreferredClients().size());
        assertEquals(c2, student.getPreferredClients().get(1));

    }

    @Test
    public void addPreferredClientAlreadyAdded() {

        //checking no clients already exist
        assertEquals(0, student.getPreferredClients().size());
        Client c = new Client("Matt", "c123", "password");

        //adding preferred client
        try {
            student.addPreferredClient(c);
        } catch (PreferredClientAlreadyExistsException e) {
        } catch (TooManyPreferredClientsException e) {
        }

        //adding same client to throw exception
        assertThrows(PreferredClientAlreadyExistsException.class, () -> student.addPreferredClient(c));

    }

    @Test
    public void addTooManyPreferredClients() {

        Client c1 = new Client("Matt", "c123", "password");
        Client c2 = new Client("Sally", "c124", "password");
        Client c3 = new Client("Alice", "c125", "password");
        Client c4 = new Client("Gandalf", "c126", "password");
        Client c5 = new Client("Gollum", "c127", "password");

        //checking no clients already exist
        assertEquals(0, student.getPreferredClients().size());

        //adding four clients
        try {
            student.addPreferredClient(c1);
            student.addPreferredClient(c2);
            student.addPreferredClient(c3);
            student.addPreferredClient(c4);
        }
        catch (PreferredClientAlreadyExistsException e) {
        } catch (TooManyPreferredClientsException e) {
        }
        assertEquals(4, student.getPreferredClients().size());

        //adding a fifth client to throw exception
        assertThrows(TooManyPreferredClientsException.class, () -> student.addPreferredClient(c5));

    }

    @Test
    public void addPreferredRole() {
        Role r1 = new Role("role1");
        Role r2 = new Role("role2");

        //checking no preferred roles already exist
        assertEquals(0, student.getPreferredRoles().size());

        //adding role
        try {
            student.addPreferredRole(r1);
        } catch (PreferredRoleAlreadyExistsException e) {
        } catch (TooManyPreferredRolesException e) {
        }

        //checking role was added
        assertEquals(1, student.getPreferredRoles().size());
        assertEquals(r1, student.getPreferredRoles().get(0));

        //adding another role
        try {
            student.addPreferredRole(r2);
        } catch (PreferredRoleAlreadyExistsException e) {
        } catch (TooManyPreferredRolesException e) {
        }

        //checking role was added
        assertEquals(2, student.getPreferredRoles().size());
        assertEquals(r2, student.getPreferredRoles().get(1));
    }

    @Test
    public void addPreferredRoleAlreadyAdded() {
        Role r1 = new Role("role1");

        //checking no preferred roles already exist
        assertEquals(0, student.getPreferredRoles().size());

        //adding role
        try {
            student.addPreferredRole(r1);
        } catch (PreferredRoleAlreadyExistsException e) {
        } catch (TooManyPreferredRolesException e) {
        }

        //adding same role to throw exception
        assertThrows(PreferredRoleAlreadyExistsException.class, () -> student.addPreferredRole(r1));

    }

    @Test
    public void addTooManyPreferredRoles() {
        Role r1 = new Role("role1");
        Role r2 = new Role("role2");
        Role r3 = new Role("role3");

        //checking no preferred roles already exist
        assertEquals(0, student.getPreferredRoles().size());

        //adding rwo roles
        try {
            student.addPreferredRole(r1);
            student.addPreferredRole(r2);
        } catch (PreferredRoleAlreadyExistsException e) {
        } catch (TooManyPreferredRolesException e) {
        }
        assertEquals(2, student.getPreferredRoles().size());

        //adding a third role to throw exception
        assertThrows(TooManyPreferredRolesException.class, () -> student.addPreferredRole(r3));
    }

    @Test
    public void addDislikedStudent() {
        Student s1 = new Student("Richie", "c123", "password");
        Student s2 = new Student("Voldemort", "gugigiu", "password");

        //checking no disliked students already exists
        assertEquals(0, student.getDislikedStudents().size());

        //adding a disliked student
        try {
            student.addDislikedStudent(s1);
        } catch (DislikedStudentAlreadyExistsException e) {
        } catch (TooManyDislikedStudentsException e) {
        } catch (AddingSelfAsDislikedStudentException e) {
        }

        //checking disliked student was added
        assertEquals(1, student.getDislikedStudents().size());
        assertEquals(s1, student.getDislikedStudents().get(0));

        //adding another disliked student
        try {
            student.addDislikedStudent(s2);
        } catch (DislikedStudentAlreadyExistsException e) {
        } catch (TooManyDislikedStudentsException e) {
        } catch (AddingSelfAsDislikedStudentException e) {
        }

        //checking disliked student was added
        assertEquals(2, student.getDislikedStudents().size());
        assertEquals(s2, student.getDislikedStudents().get(1));
    }

    @Test
    public void addDislikedStudentAlreadyAdded() {

        Student s1 = new Student("Richie", "c123", "password");

        //checking no disliked students already exists
        assertEquals(0, student.getDislikedStudents().size());

        //adding disliked student
        try {
            student.addDislikedStudent(s1);
        } catch (DislikedStudentAlreadyExistsException e) {
        } catch (TooManyDislikedStudentsException e) {
        } catch (AddingSelfAsDislikedStudentException e) {
        }

        //adding another disliked student
        assertThrows(DislikedStudentAlreadyExistsException.class, () -> student.addDislikedStudent(s1));
    }

    @Test
    public void addTooManyDislikedStudents() {
        Student s1 = new Student("Richie", "c123", "password");
        Student s2 = new Student("Patrick", "c124", "password");
        Student s3 = new Student("Frodo", "c125", "password");
        Student s4 = new Student("Legolas", "c126", "password");

        //checking no disliked students already exists
        assertEquals(0, student.getDislikedStudents().size());

        //adding a 3 disliked students
        try {
            student.addDislikedStudent(s1);
            student.addDislikedStudent(s2);
            student.addDislikedStudent(s3);
        } catch (DislikedStudentAlreadyExistsException e) {
        } catch (TooManyDislikedStudentsException e) {
        } catch (AddingSelfAsDislikedStudentException e) {
        }

        assertEquals(3, student.getDislikedStudents().size());

        //adding fourth disliked student to throw exception
        assertThrows(TooManyDislikedStudentsException.class, () -> student.addDislikedStudent(s4));

    }

    @Test
    public void addSelfToDislikedStudents() {
        assertThrows(AddingSelfAsDislikedStudentException.class, () -> student.addDislikedStudent(student));
    }
}
