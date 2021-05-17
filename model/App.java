package au.edu.rmit.projectmanager.model;

import au.edu.rmit.projectmanager.exceptions.*;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class App {

    private static final String fileName = "./src/au/edu/rmit/projectmanager/data.txt";

    private final int MIN_STUDENTS_WITH_HIGH_GPA = 2;
    private final int MIN_STUDENTS_WITH_EXPERIENCE = 1;
    private final int MIN_PERSONALITY_A_OR_B = 1;

    private double gpaThreshold = 3.5;
    private double maxScoreDifference = 5;
    private boolean allocationIsComplete = false;

    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Constraint> constraints = new ArrayList<>();
    private ArrayList<Role> roles = new ArrayList<>();
    private User currentUser = null;

    public App() {
        generateSoftConstraints();
        loadData();
    }

    /**
     * Load saved data from binary file into system
     */
    private void loadData() {
        try {
            FileInputStream file = new FileInputStream(this.fileName);
            ObjectInputStream in = new ObjectInputStream(file);

            this.users = (ArrayList) in.readObject();
            this.constraints = (ArrayList) in.readObject();
            this.roles = (ArrayList) in.readObject();

            in.close();
            file.close();
        } catch (EOFException ex) {
            System.out.println("System tried to load data, but no data was found. Please Check.");
        } catch (IOException ex) {
            System.out.println("IOException is caught");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
        }
    }

    /**
     * Save data from memory to binary file
     */
    private void saveData() {
        try {
            FileOutputStream file = new FileOutputStream(this.fileName);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(this.users);
            out.writeObject(this.constraints);
            out.writeObject(this.roles);

            out.close();
            file.close();

            System.out.println("Data has been saved");

        } catch (IOException ex) {
            System.out.println("Something went wrong while saving the file");
        }
    }

    public void addUser(User u) {
        this.users.add(u);
    }

    public void removeAllUsers() {
        this.users.clear();
    }

    /**
     * Return a student if his username exists in the system
     *
     * @param username Username to check
     * @return The User reference
     * @throws UserDoesNotExistException User does not exist in the system
     */
    public Student getStudentByUsername(String username) throws UserDoesNotExistException {
        for (Student s : this.getAllStudents()) {
            if (s.getUsername().equals(username)) {
                return s;
            }
        }
        throw new UserDoesNotExistException();
    }

    /**
     * Returns all projects from all clients in system
     *
     * @return All projects in system
     */
    public ArrayList<Project> getAllProjects() {
        ArrayList<Project> projects = new ArrayList<>();
        ArrayList<Client> clients = getAllClients();

        for (Client client : clients) {
            for (int i = 0; i < client.getProjects().size(); i++)
                projects.add(client.getProjects().get(i));
        }
        return projects;
    }

    /**
     * Return all roles created in all projects
     *
     * @return All roles in system
     */
    public ArrayList<Role> getAllRoles() {

        ArrayList<Client> clients = getAllClients();
        ArrayList<Role> roleList = new ArrayList<>();

        for (Client client : clients) {
            for (Project p : client.getProjects()) {
                if (p.getRoles() != null) {
                    for (Role r : p.getRoles()) {
                        if (!isAlreadyAdded(roleList, r))
                            roleList.add(r);
                    }
                }
            }
        }
        return roleList;
    }

    /**
     * Checks if a role is already present in a role list
     *
     * @param roleList The role list
     * @param role     The new role
     * @return Whether the role is already present in the list
     */
    private boolean isAlreadyAdded(ArrayList<Role> roleList, Role role) {
        for (Role role2 : roleList) {
            if (role2.getTitle().equalsIgnoreCase(role.getTitle()))
                return true;
        }
        return false;
    }


    /**
     * Return all teams in system
     *
     * @return All teams
     */
    public ArrayList<Team> getAllTeams() {
        ArrayList<Team> teams = new ArrayList<>();
        for (Project p : getAllProjects()) {
            if (p.getTeam() != null) {
                teams.add(p.getTeam());
            }
        }
        return teams;
    }

    /**
     * Returns all clients in system
     *
     * @return All clients
     */
    public ArrayList<Client> getAllClients() {
        ArrayList<Client> clients = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Client)
                clients.add((Client) user);
        }
        return clients;
    }

    /**
     * Return all students in system
     *
     * @return All students
     */
    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> students = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Student)
                students.add((Student) user);
        }
        return students;
    }

    /**
     * Create and save a new user into the system.
     *
     * @param username The user's unique username
     * @param password The user's password
     * @param name     The user's name
     * @param userType The user type
     * @return The newly created user
     * @throws UserAlreadyExistsException The username is already assigned to another user
     */
    public User createUser(String username, String password, String name, Types.UserType userType)
            throws UserAlreadyExistsException, InvalidUsernameException {
        if (username.equals(""))
            throw new InvalidUsernameException();

        for (User user : users) {
            if (user.getUsername().toLowerCase().equals(username.toLowerCase()))
                throw new UserAlreadyExistsException();
        }

        User newUser = null;
        switch (userType) {
            case Student:
                newUser = new Student(name, username, password);
                break;
            case Client:
                newUser = new Client(name, username, password);
                break;
            default:
                newUser = new Manager(name, username, password);
                break;
        }

        this.users.add(newUser);
        return newUser;
    }

    /**
     * Generate all the soft constraints
     */
    private void generateSoftConstraints() {
        constraints.add(new Constraint("At least one member of personality type A or B"));
        constraints.add(new Constraint("No more than one member of each personality type"));
        constraints.add(new Constraint("One member with 5+ years of experience"));
        constraints.add(new Constraint("Match student with preferred client"));
        constraints.add(new Constraint("Don't match student with disliked student"));
        constraints.add(new Constraint("Match preferred role of student with required role of project"));
    }

    /**
     * Sets the personality type of a specified student
     *
     * @param student The student to update
     * @param pt      The personality type
     */
    public void setPersonalityType(Student student, Types.PersonalityType pt) {
        student.setPersonalityType(pt);
    }

    /**
     * Sets the GPA threshold for team allocation
     *
     * @param threshold GPA threshold
     */
    public void setGpaThreshold(double threshold) throws InvalidGpaThresholdException {
        if (threshold >= 1.0 && threshold <= 4.0)
            this.gpaThreshold = threshold;
        else
            throw new InvalidGpaThresholdException();
    }

    /**
     * Returns current GPA Threshold for team allocations
     *
     * @return GPA Threshold
     */
    public double getGpaThreshold() {
        return gpaThreshold;
    }

    /**
     * Returns whether allocation is complete or not
     *
     * @return If allocation is complete
     */
    public boolean isAllocationComplete() {
        return allocationIsComplete;
    }

    /**
     * Sets the weight of a soft constraint (permitted value: 1 to 4)
     *
     * @param constraint The constraint
     * @param weight     The new weight
     */
    public void setConstraintWeight(Constraint constraint, double weight) throws InvalidConstraintWeightException {
        if (constraint != null && weight >= 1.0 && weight <= 4.0)
            constraint.setWeight(weight);
        else
            throw new InvalidConstraintWeightException();
    }

    /**
     * Sets the max score difference that the manager allows for manual student swaps
     *
     * @param maxScoreDifference The maximal score difference
     */
    public void setMaxScoreDifference(double maxScoreDifference) {
        this.maxScoreDifference = maxScoreDifference;
    }

    /**
     * Sorts an array by female first, male second
     *
     * @return The new sorted array
     */
    private ArrayList<Queue<Student>> splitStudentsByGender() {
        Queue<Student> females = new LinkedList<>();
        Queue<Student> males = new LinkedList<>();

        for (Student student : this.getAllStudents()) {
            if (student.getGender() != null) {
                if (student.getGender().equals(Types.Gender.Female)) {
                    females.add(student);
                } else {
                    males.add(student);
                }
            }
        }

        ArrayList<Queue<Student>> lst = new ArrayList<>();
        lst.add(females);
        lst.add(males);

        return lst;
    }

    /**
     * Tries to allocate each student to a team in an optimal fashion
     *
     * @throws TeamAlreadyFullException The team is already full and cannot receive new users
     */
    public void allocateTeams() throws TeamAlreadyFullException {
        // Define variables we need for this process
        ArrayList<Team> studentTeams = new ArrayList<>();
        ArrayList<Queue<Student>> lst = this.splitStudentsByGender();

        Queue<Student> femaleStudents = lst.get(0);
        Queue<Student> maleStudents = lst.get(1);

        // Step 1: Create all the required student teams
        for (Project project : this.getAllProjects()) {
            Team studentTeam = new Team(project);
            studentTeams.add(studentTeam);
        }

        // Step 2: Add a female to each team
        for (Team studentTeam : studentTeams) {
            if (!femaleStudents.isEmpty()) {
                studentTeam.addStudent(femaleStudents.poll());
            }
        }

        // Step 3: Fill the rest with male students and check that averageGPA does not exceed threshold
        for (Team studentTeam : studentTeams) {
            while (!maleStudents.isEmpty() && !studentTeam.isFull()) {
                studentTeam.addStudent(maleStudents.poll());
            }
        }

        for (int i = 0; i < studentTeams.size(); i++) {
            for (int j = i + 1; j < studentTeams.size(); j++) {

                Team studentTeamOne = studentTeams.get(i);
                Team studentTeamTwo = studentTeams.get(j);

                for (int k = 0; k < studentTeamOne.getTeamSize(); k++) {
                    for (int l = 0; l < studentTeamTwo.getTeamSize(); l++) {
                        swapStudentsIfFavorable(
                                studentTeamOne.getStudentAtIndex(k),
                                studentTeamTwo.getStudentAtIndex(l)
                        );
                    }
                }
            }
        }
        this.allocationIsComplete = true;

    }

    /**
     * Swap 2 students if the team score delta for either or both teams improve
     *
     * @param s1 First student to swap
     * @param s2 Second student to swap
     * @throws TeamAlreadyFullException The team is already full and cannot receive new users
     */
    public void swapStudentsIfFavorable(Student s1, Student s2) throws TeamAlreadyFullException {
        double team1CurrentScore = s1.getTeam().getTeamScore();
        double team2CurrentScore = s2.getTeam().getTeamScore();

        swapStudents(s1, s2);

        double team1NewScore = s2.getTeam().getTeamScore();
        double team2NewScore = s1.getTeam().getTeamScore();

        double delta = (team1NewScore - team1CurrentScore) + (team2NewScore - team2CurrentScore);
        if (s1.getTeam().hasNoMoreThanOneFemaleMember()
                && s2.getTeam().hasNoMoreThanOneFemaleMember()
                && s1.getTeam().getAverageGPA() > gpaThreshold
                && s2.getTeam().getAverageGPA() > gpaThreshold
                && delta > 0
                && delta < maxScoreDifference
        ) {
            System.out.println("Swapped: " + s1.getUsername() + " and " + s2.getUsername());
        } else {
            swapStudents(s1, s2);
        }
    }

    /**
     * Swap 2 students
     *
     * @param s1 First student
     * @param s2 Second student
     * @throws TeamAlreadyFullException The team is already full and cannot receive new users
     */
    public void swapStudents(Student s1, Student s2) throws TeamAlreadyFullException {
        Team t1 = s1.getTeam();
        Team t2 = s2.getTeam();

        try {
            t1.removeStudent(s1);
            t1.addStudent(s2);

            t2.removeStudent(s2);
            t2.addStudent(s1);
        } catch (UserDoesNotExistException e) {
        }
    }

    /**
     * Log in an user into the system
     *
     * @param username Username to validate
     * @param password Password to validate
     * @return Reference to the user found in the system
     * @throws UserDoesNotExistException   The user does not exist in the system
     * @throws InvalidCredentialsException The credentials provided are invalid
     */
    public User login(String username, String password) throws UserDoesNotExistException, InvalidCredentialsException {
        for (User u : this.users) {
            if (User.doUsernamesMatch(u.username, username)) {
                if (User.doPasswordsMatch(u.password, password)) {
                    this.currentUser = u;
                    return u;
                } else throw new InvalidCredentialsException();
            }
        }
        throw new UserDoesNotExistException();
    }

    /**
     * Returns all users in system
     *
     * @return All users
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Returns all constraints in system
     *
     * @return All constraints
     */
    public ArrayList<Constraint> getConstraints() {
        return constraints;
    }

    /**
     * Save data before closing the app
     */
    public void exit() {
        saveData();
        System.exit(0);
    }

    /**
     * Removes an user from the system
     *
     * @param userToRemove The user to be removed
     * @throws UserDoesNotExistException
     */
    public void removeUser(User userToRemove) throws UserDoesNotExistException {
        if (users.contains(userToRemove))
            users.remove(userToRemove);
        else
            throw new UserDoesNotExistException();
    }

    /**
     * Return currently logged in user
     *
     * @return Currently logged in user
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Check if the username provided is already registered in the system
     *
     * @param username Username provided
     * @return Whether the username is unique
     */
    public boolean isUsernameUnique(String username) {
        for (User user : users) {
            if (user.getUsername().toLowerCase().equals(username.toLowerCase()))
                return false;
        }
        return true;
    }
}
