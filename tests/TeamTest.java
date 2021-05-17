package au.edu.rmit.projectmanager.tests;

import au.edu.rmit.projectmanager.exceptions.GpaOutOfBoundsException;
import au.edu.rmit.projectmanager.exceptions.TeamAlreadyFullException;
import au.edu.rmit.projectmanager.exceptions.UserDoesNotExistException;
import au.edu.rmit.projectmanager.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    Team team;

    @BeforeEach
    void setUp() {
        Client client = new Client("Test client", "yoyoh", "abcd123");
        ArrayList<String> desiredFrameworks = new ArrayList<>();
        desiredFrameworks.add("Swing");
        desiredFrameworks.add("Boostrap");
        ArrayList<String> desiredLanguages = new ArrayList<>();
        desiredLanguages.add("Java");
        desiredLanguages.add("CSS");
        ArrayList<Role> desiredRoles = new ArrayList<>();
        desiredRoles.add(new Role("UX Designer", desiredFrameworks, desiredLanguages));
        Project project = new Project(client, "Test project", desiredRoles);
        team = new Team(project);
    }

    @Test
    void addStudent() {
        String s1 = "student1", s2 = "student2", s3 = "student3", s4 = "student4", s5 = "student5";

        // Check that team is initially empty
        int defaultStudentCount = 0;
        assertEquals(defaultStudentCount, team.getStudents().size());

        // Add 4 new students
        try {
            team.addStudent(new Student("Ricky", s1, "chad22"));
            team.addStudent(new Student("Becky", s2, "chad22"));
            team.addStudent(new Student("Nicky", s3, "chad22"));
            team.addStudent(new Student("Chicky", s4, "chad22"));
        } catch (TeamAlreadyFullException e) {
            System.out.println(e.getMessage());
        }

        // Check that all 4 students have been added
        assertEquals(s1, team.getStudents().get(0).getUsername());
        assertEquals(s2, team.getStudents().get(1).getUsername());
        assertEquals(s3, team.getStudents().get(2).getUsername());
        assertEquals(s4, team.getStudents().get(3).getUsername());

        // Try to add a 5th student
        assertThrows(TeamAlreadyFullException.class, () -> team.addStudent(new Student("Flicky", s5, "chad22")));
    }

    @Test
    void removeStudent() {
        Student s1 = new Student("jorry", "manzo", "checck");

        // Check that team is initially empty
        int defaultStudentCount = 0;
        assertEquals(defaultStudentCount, team.getStudents().size());

        // Try to remove student that hasn't been added yet
        assertThrows(UserDoesNotExistException.class, () -> team.removeStudent(s1));

        // Add new student
        try {
            team.addStudent(s1);
        } catch (TeamAlreadyFullException e) {
        }

        // Remove newly added student
        try {
            team.removeStudent(s1);
        } catch (UserDoesNotExistException e) {
        }

        // Check that team is empty
        assertEquals(defaultStudentCount, team.getStudents().size());
    }

    @Test
    void getTeamScore() {
        fail(); // TODO: remove when implemented
    }

    @Test
    void getTotalGPA() {
        double s1GPA = 3.2;
        double s2GPA = 1.5;
        double s3GPA = 2.7;

        // Check that initial GPA is 0
        int defaultTeamGPA = 0;
        assertEquals(defaultTeamGPA, team.getTotalGPA());

        // Add 3 students to team
        Student s1 = new Student("jofrry", "gdrg", "checck");
        Student s2 = new Student("sgesg", "mangdrgdrzo", "ges");
        Student s3 = new Student("sefg", "grdg", "3r32rf");

        try {
            team.addStudent(s1);
            team.addStudent(s2);
            team.addStudent(s3);
        } catch (TeamAlreadyFullException e) {
        }

        // Set their new GPA
        try {
            s1.setGpa(s1GPA);
            s2.setGpa(s2GPA);
            s3.setGpa(s3GPA);
        } catch (GpaOutOfBoundsException e) {
        }

        // Manually calculate total GPA
        double expectedGPATotal = s1GPA + s2GPA + s3GPA;

        // Calculate total GPA using getTotalGPA
        double actualGPATotal = team.getTotalGPA();

        // Check that calculated and actual totals correspond
        assertEquals(expectedGPATotal, actualGPATotal);
    }

    @Test
    void hasNoMoreThanOneFemaleMember() {
        // Check that team is initially empty
        int defaultStudentCount = 0;
        assertEquals(defaultStudentCount, team.getStudents().size());

        // Check when team is empty
        assertTrue(team.hasNoMoreThanOneFemaleMember());

        // Check when team has 1 female
        Student femaleStudent1 = new Student("fesfsef", "fesfesfse", "fesfesf");
        femaleStudent1.setGender(Types.Gender.Female);
        try {
            team.addStudent(femaleStudent1);
        } catch (TeamAlreadyFullException e) {
        }
        assertTrue(team.hasNoMoreThanOneFemaleMember());

        // Check when team has 2 females
        Student femaleStudent2 = new Student("gdrgcr", "htfhtf", "gdrgdr");
        femaleStudent2.setGender(Types.Gender.Female);
        try {
            team.addStudent(femaleStudent1);
        } catch (TeamAlreadyFullException e) {
        }
        assertFalse(team.hasNoMoreThanOneFemaleMember());
    }

    @Test
    void isFull() {
        // Check that team is initially empty
        int defaultStudentCount = 0;
        assertEquals(defaultStudentCount, team.getStudents().size());

        // Check when team has 3 students
        try {
            team.addStudent(new Student("fsfs4fs3", "ecesfesfse", "fesfesfsef"));
            team.addStudent(new Student("hdrhtfh", "hfthfthf", "jftjhft"));
            team.addStudent(new Student("turtryu", "urrty", "vgnvb"));
        } catch (TeamAlreadyFullException e) {
        }
        assertFalse(team.isFull());

        // Check when team has 4 students
        try {
            team.addStudent(new Student("jfgnvn", "vngnvgn", "bfcbfbc"));
        } catch (TeamAlreadyFullException e) {
        }
        assertTrue(team.isFull());

    }
}