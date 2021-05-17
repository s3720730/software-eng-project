package au.edu.rmit.projectmanager.tests;

import au.edu.rmit.projectmanager.exceptions.*;
import au.edu.rmit.projectmanager.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    App app;

    @BeforeEach
    void setUp() {
        app = new App();
    }

    @Test
    void loadData() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Method method = App.class.getDeclaredMethod("loadData");
        method.setAccessible(true);
        method.invoke(this.app);

        try {
            Student student = app.getStudentByUsername("Vlad");
            assertEquals("The Ripper", student.getName());
        } catch (UserDoesNotExistException ex) {
            fail();
        }
    }

    @Test
    void saveData() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, UserDoesNotExistException {
        App app2 = new App();

        try {
            app.removeUser(app.getStudentByUsername("Vlad"));
        } catch (UserDoesNotExistException ex) {
            System.out.println("No User To Be Deleted");
        }

        try {
            this.app.createUser("Vlad", "test", "The Ripper", Types.UserType.Student);
        } catch (UserAlreadyExistsException | InvalidUsernameException e) {
            e.printStackTrace();
        }

        Method saveMethod = App.class.getDeclaredMethod("saveData");
        saveMethod.setAccessible(true);
        saveMethod.invoke(this.app);

        app.removeUser(app.getStudentByUsername("Vlad"));

        Method loadMethod = App.class.getDeclaredMethod("loadData");
        loadMethod.setAccessible(true);
        loadMethod.invoke(app2);

        Student student = app2.getStudentByUsername("Vlad");
        assertEquals(student.getName(), "The Ripper");

    }

    @Test
    void getAllProjects() {
        // Manually count all the existing projects
        int expectedProjectCount = 0;
        ArrayList<Client> clients = app.getAllClients();
        for (Client c : clients) {
            for (Project p : c.getProjects()) {
                expectedProjectCount++;
            }
        }

        // Count projects using getAllProjects
        int actualProjectCount = app.getAllProjects().size();

        // Check that the expected and actual project counts correspond
        assertEquals(expectedProjectCount, actualProjectCount);
    }

    @Test
    void getAllClients() {
        // Manually count all clients in the system
        ArrayList<User> allUsers = app.getUsers();
        int expectedClientCount = 0;
        for (User u : allUsers) {
            if (u instanceof Client)
                expectedClientCount++;
        }

        // Count clients using getAllClients()
        int actualClientCount = app.getAllClients().size();

        // Check that expected and actual client counts correspond
        assertEquals(expectedClientCount, actualClientCount);
    }

    @Test
    void getAllStudents() {
        // Manually count all students in the system
        ArrayList<User> allUsers = app.getUsers();
        int expectedStudentCount = 0;
        for (User u : allUsers) {
            if (u instanceof Student)
                expectedStudentCount++;
        }

        // Count clients using getAllClients()
        int actualStudentCount = app.getAllStudents().size();

        // Check that expected and actual client counts correspond
        assertEquals(expectedStudentCount, actualStudentCount);
    }

    @Test
    void createUser() {
        String newUser1 = "jacckie";
        String newUser2 = "";

        // Check that new user doesn't already exist in the system
        boolean userAlreadyExists = false;
        for (User u : app.getUsers()) {
            if (u.getUsername().toLowerCase().equals(newUser1.toLowerCase()))
                userAlreadyExists = true;
        }
        assertFalse(userAlreadyExists);

        // Create new user
        try {
            app.createUser(newUser1, "neee33", "Maximus", Types.UserType.Student);
        } catch (Exception e) {
        }

        // Check that new user has been created in the system
        for (User u : app.getUsers()) {
            if (u.getUsername().toLowerCase().equals(newUser1.toLowerCase()))
                userAlreadyExists = true;
        }
        assertTrue(userAlreadyExists);

        // Check that creating an identical user throws an error
        assertThrows(UserAlreadyExistsException.class,
                () -> app.createUser(newUser1, "deerr", "Lee", Types.UserType.Student));

        // Check that creating a user with an invalid username throws an error
        assertThrows(InvalidUsernameException.class,
                () -> app.createUser(newUser2, "fesf", "fesfes", Types.UserType.Client));
    }

    @Test
    void setPersonalityType() {
        Types.PersonalityType newPersonalityType = Types.PersonalityType.B;

        Student student = new Student("fesfesfse", "gdrgdrgdr", "fsefsefsef2");

        // Check that personality type is null by default
        assertNull(student.getPersonalityType());

        // Change personality type
        app.setPersonalityType(student, newPersonalityType);

        // Check that personality type has been updated
        assertEquals(newPersonalityType, student.getPersonalityType());
    }

    @Test
    void generateSoftConstraints() {
        // This method is already called when instantiating the App, so just test that the soft constraints are there
        int expectedConstraintsCount = 6;
        assertEquals(expectedConstraintsCount, app.getConstraints().size());
    }

    @Test
    void setConstraintWeight() {
        int defaultConstraintWeight = 1;
        int newConstraintWeight = 3;
        int invalidConstraintWeight = -5;

        // Check that constraint has default weight after creation
        Constraint constraint = new Constraint("My soft constraint");
        assertEquals(defaultConstraintWeight, constraint.getWeight());

        // Set new constraint weight
        try {
            app.setConstraintWeight(constraint, newConstraintWeight);
        } catch (InvalidConstraintWeightException e) {
        }

        // Check that valid entry updates constraint
        assertEquals(newConstraintWeight, constraint.getWeight());

        // Check that invalid entry is signaled (valid entry is between 1.0 and 4.0)
        assertThrows(InvalidConstraintWeightException.class, () -> app.setConstraintWeight(constraint, invalidConstraintWeight));
    }

    @Test
    void setGpaThreshold() {
        double newGPA = 2.523;
        double invalidGPA = -4.2;

        try {
            app.setGpaThreshold(newGPA);
        } catch (InvalidGpaThresholdException e) {
        }

        // Check that valid entry updates GPA threshold
        assertEquals(newGPA, app.getGpaThreshold());

        // Check that invalid entry is signaled (valid entry is between 1.0 and 4.0)
        assertThrows(InvalidGpaThresholdException.class, () -> app.setGpaThreshold(invalidGPA));
    }

    @Test
    void allocateTeams() throws ProjectAlreadyExistsException,
            GpaOutOfBoundsException, PreferredClientAlreadyExistsException, TooManyPreferredClientsException,
            TooManyPreferredRolesException, PreferredRoleAlreadyExistsException, TeamAlreadyFullException, UserDoesNotExistException {

        // Make sure there are no teams
        assertEquals(0, app.getAllTeams().size());

        app.removeAllUsers();
        assertEquals(0, app.getUsers().size());

        // Create two clients
        Client c1 = new Client("A", "A", "A");
        Client c2 = new Client("B", "B", "B");

        app.addUser(c1);
        app.addUser(c2);

        // Create two projects
        Project p1 = new Project(c1, "Project A");
        Project p2 = new Project(c2, "Project B");

        // Add the projects to the clients project list
        c1.addProject(p1);
        c2.addProject(p2);

        // Create a set of roles
        ArrayList<String> langs_p1 = new ArrayList<>(Arrays.asList(
                "Java",
                "Swift",
                "C#",
                "Python"
        ));
        ArrayList<String> frameworks_p1 = new ArrayList<>(Arrays.asList(
                "Swing",
                "AlamoFire",
                ".NET",
                "TensorFlow"
        ));

        ArrayList<String> langs_p2 = new ArrayList<>(Arrays.asList(
                "JavaScript",
                "JavaScript",
                "JavaScript",
                "Python"
        ));
        ArrayList<String> frameworks_p2 = new ArrayList<>(Arrays.asList(
                "ReactJS",
                "ExpressJS",
                "VueJS",
                "Django"
        ));

        ArrayList<Role> roles_p1 = new ArrayList<Role>(Arrays.asList(
                new Role("Java",
                        new ArrayList<String>(Arrays.asList(langs_p1.get(0))),
                        new ArrayList<String>(Arrays.asList(frameworks_p1.get(0)))),
                new Role("Swift",
                        new ArrayList<String>(Arrays.asList(langs_p1.get(1))),
                        new ArrayList<String>(Arrays.asList(frameworks_p1.get(1)))),
                new Role("C#",
                        new ArrayList<String>(Arrays.asList(langs_p1.get(2))),
                        new ArrayList<String>(Arrays.asList(frameworks_p1.get(2)))),
                new Role("Python",
                        new ArrayList<String>(Arrays.asList(langs_p1.get(3))),
                        new ArrayList<String>(Arrays.asList(frameworks_p1.get(3))))
        ));

        ArrayList<Role> roles_p2 = new ArrayList<Role>(Arrays.asList(
                new Role("Javascript 1",
                        new ArrayList<String>(Arrays.asList(langs_p2.get(0))),
                        new ArrayList<String>(Arrays.asList(frameworks_p2.get(0)))),
                new Role("Javascript 2",
                        new ArrayList<String>(Arrays.asList(langs_p2.get(1))),
                        new ArrayList<String>(Arrays.asList(frameworks_p2.get(1)))),
                new Role("Javascript 3",
                        new ArrayList<String>(Arrays.asList(langs_p2.get(2))),
                        new ArrayList<String>(Arrays.asList(frameworks_p2.get(2)))),
                new Role("Python Backend",
                        new ArrayList<String>(Arrays.asList(langs_p2.get(3))),
                        new ArrayList<String>(Arrays.asList(frameworks_p2.get(3))))
        ));

        // Add the roles to the projects
        p1.setRoles(roles_p1);
        p2.setRoles(roles_p2);

        // Create eight students with the correct requirements
        int students = 8;
        for (int i = 0; i < students; i++) {
            Student s = new Student("Name" + i, "Username" + i, "pass");

            s.updateProfile(i % 4 == 0 ? Types.Gender.Female : Types.Gender.Male,3.0, i);
            s.addPreferredClient(i % 2 == 0 ? c1 : c2);
            s.setPersonalityType(Types.PersonalityType.values()[i % 5]);

            for (int j = 0; j < 2; j++) {
                s.addPreferredRole(i % 2 == 0 ? roles_p2.get((i + j) % 3) : roles_p1.get((i + j) % 3));
            }

            app.addUser(s);
        }

        app.allocateTeams();

       ArrayList<Team> teams = app.getAllTeams();

       // Check if the right students are in the right teams
        ArrayList<Student> team1 = new ArrayList<>(Arrays.asList(
                app.getStudentByUsername("Username0"),
                app.getStudentByUsername("Username2"),
                app.getStudentByUsername("Username3"),
                app.getStudentByUsername("Username1")
        ));

        ArrayList<Student> team2 = new ArrayList<>(Arrays.asList(
                app.getStudentByUsername("Username5"),
                app.getStudentByUsername("Username7"),
                app.getStudentByUsername("Username6"),
                app.getStudentByUsername("Username4")
        ));

        assertEquals(team1, teams.get(0).getStudents());
        assertEquals(team2, teams.get(1).getStudents());
    }

    @Test
    void swapStudents() {
        Client c1 = new Client("abc", "def", "ghi");
        Project p1 = new Project(c1, "Project1");
        Team t1 = new Team(p1);

        Project p2 = new Project(c1, "Project2");
        Team t2 = new Team(p2);

        // Check that t1 and t2 are empty at first
        int defaultStudentCount = 0;
        assertEquals(defaultStudentCount, t1.getStudents().size());
        assertEquals(defaultStudentCount, t2.getStudents().size());

        // Add different students to each team
        Student s1 = new Student("meh", "diz", "idour");
        try {
            t1.addStudent(s1);
        } catch (Exception e) {
        }
        ;

        Student s2 = new Student("ges", "fes", "e3wef");
        try {
            t2.addStudent(s2);
        } catch (Exception e) {
        }
        ;

        // Check that s1 and s2 have been added their respective projects
        assertEquals(t1, s1.getTeam());
        assertEquals(t2, s2.getTeam());

        // Swap teams
        try {
            app.swapStudents(s1, s2);
        } catch (TeamAlreadyFullException e) {
        }
        ;

        // Check that s1 and s2 have been swapped
        assertEquals(t2, s1.getTeam());
        assertEquals(t1, s2.getTeam());
    }

    @Test
    void login() {
        String username = "Will";
        String expectedName = "Smith";

        try {
            this.app.createUser(username, "ilikeunicorns101", expectedName, Types.UserType.Student);
        } catch (Exception e) {

        }

        try {
            User loggedUser = app.login(username, "ilikeunicorns101");
            assertEquals(expectedName, loggedUser.getName());
        } catch (UserDoesNotExistException ex) {
            fail();
        } catch (InvalidCredentialsException ex) {
            fail();
        }

    }

    @Test
    void loginException() {
        try {
            this.app.createUser("Will", "ilikeunicorns101", "Smith", Types.UserType.Student);
        } catch (Exception e) {
        }

        assertThrows(InvalidCredentialsException.class, () -> app.login("Will", "wrong-password"));
    }

    @Test
    void loginUserDoesNotExist() {
        assertThrows(UserDoesNotExistException.class, () -> app.login("This-user-does-not-exist", "wrong-password"));
    }

    @Test
    void removeUser() {
        String invalidUser = "Jacqui";
        Client client = new Client(invalidUser, "john", "doe");

        // Check if deleting a user that's not in the system is flagged
        assertThrows(UserDoesNotExistException.class, () -> app.removeUser(client));

        // Create a valid user in the system
        String validUser = "Mika";
        try {
            app.createUser(validUser, "neee33", "Maximus", Types.UserType.Student);
        } catch (Exception e) {
        }

        // Check that valid user is in the system
        User existingUser = null;
        boolean userCreated = false;
        ArrayList<User> users = app.getUsers();
        for (User u : users) {
            if (u.getUsername().equals(validUser)) {
                userCreated = true;
                existingUser = u;
            }
        }
        assertTrue(userCreated);

        // Remove user
        try {
            app.removeUser(existingUser);
        } catch (UserDoesNotExistException e) {
        }

        // Check that deleted user is not in system
        users = app.getUsers();
        userCreated = false;
        for (User u : users) {
            if (u.getUsername().equals(validUser)) {
                userCreated = true;
            }
        }
        assertFalse(userCreated);
    }


}
