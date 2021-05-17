
package au.edu.rmit.projectmanager.model;


import au.edu.rmit.projectmanager.exceptions.TooManyPreferredRolesException;
import au.edu.rmit.projectmanager.model.Types.*;
import au.edu.rmit.projectmanager.exceptions.*;


import java.util.ArrayList;


public class Student extends User {

    private static final long serialVersionUID = 6529685098267756670L;

    private Gender gender = null;
    private PersonalityType personalityType = null;
    private double gpa = 0.0;
    private int yearsOfExperience = 0;
    private ArrayList<Student> dislikedStudents = new ArrayList<>();
    private ArrayList<Role> preferredRoles = new ArrayList<>();
    private ArrayList<Client> preferredClients = new ArrayList<>();
    private Team team;

    public Student(String name, String userName, String password) {
        super(name, userName, password);
    }

    /**
     * Update the student's profile
     *
     * @param gender            Gender
     * @param gpa               GPA
     * @param yearsOfExperience Number of years of experience
     * @throws GpaOutOfBoundsException The GPA is out of bounds
     */
    public void updateProfile(Gender gender, double gpa, int yearsOfExperience) throws GpaOutOfBoundsException {
        this.gender = gender;
        this.yearsOfExperience = yearsOfExperience;
        if (gpa >= 0.1 && gpa <= 4.0)
            this.gpa = gpa;
        else
            throw new GpaOutOfBoundsException();
    }

    /**
     * Add a client to the preferred clients list
     *
     * @param client Client to be added
     * @throws PreferredClientAlreadyExistsException The client is already in the list
     * @throws TooManyPreferredClientsException      The student has too many preferred clients
     */
    public void addPreferredClient(Client client) throws PreferredClientAlreadyExistsException, TooManyPreferredClientsException {

        if (preferredClients.size() == 4)
            throw new TooManyPreferredClientsException();

        for (Client c : preferredClients) {
            if (c == client)
                throw new PreferredClientAlreadyExistsException();
        }

        this.preferredClients.add(client);
    }

    /**
     * Add a role to the preferred roles list
     *
     * @param preferredRole Role to be added
     * @throws PreferredRoleAlreadyExistsException The role is already in the list
     * @throws TooManyPreferredRolesException      The student has too many preferred roles
     */
    public void addPreferredRole(Role preferredRole) throws PreferredRoleAlreadyExistsException, TooManyPreferredRolesException {

        if (preferredRoles.size() == 2)
            throw new TooManyPreferredRolesException();

        for (Role r : preferredRoles) {
            if (r == preferredRole)
                throw new PreferredRoleAlreadyExistsException();
        }
        this.preferredRoles.add(preferredRole);
    }

    /**
     * Add a student to the disliked students list
     *
     * @param student Student to be added
     * @throws DislikedStudentAlreadyExistsException The student is already in the list
     * @throws TooManyDislikedStudentsException      The student has too many disliked students
     * @throws AddingSelfAsDislikedStudentException  The student cannot dislike himself
     */
    public void addDislikedStudent(Student student) throws DislikedStudentAlreadyExistsException,
            TooManyDislikedStudentsException, AddingSelfAsDislikedStudentException {

        if (dislikedStudents.size() == 3)
            throw new TooManyDislikedStudentsException();

        if (student == this)
            throw new AddingSelfAsDislikedStudentException();

        for (Student s : dislikedStudents) {
            if (s == student)
                throw new DislikedStudentAlreadyExistsException();
        }
        this.dislikedStudents.add(student);
    }

    /**
     * Set the student GPA if between correct bounds
     *
     * @param gpa The GPA
     * @throws GpaOutOfBoundsException GPA out of bounds
     */
    public void setGpa(Double gpa) throws GpaOutOfBoundsException {
        if (gpa >= 0.1 && gpa <= 4.0)
            this.gpa = gpa;
        else
            throw new GpaOutOfBoundsException();
    }

    public ArrayList<Role> getPreferredRoles() {
        return this.preferredRoles;
    }

    public ArrayList<Client> getPreferredClients() {
        return this.preferredClients;
    }

    public ArrayList<Student> getDislikedStudents() {
        return this.dislikedStudents;
    }

    public void setPersonalityType(PersonalityType personalityType) {
        this.personalityType = personalityType;
    }

    public PersonalityType getPersonalityType() {
        return personalityType;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return this.team;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public double getGpa() {
        return gpa;
    }

    public Gender getGender() {
        return gender;
    }

    @Override
    public String toString() {
        String s = "[Name: " + name + "][GPA: " + gpa + "][Gender: " + gender + "][Experience: " + yearsOfExperience + "]" +
                "[Personality: " + personalityType + "][Username: " + username + "][Passwoord: " + password + "]" +
                "[Roles: " + preferredRoles + "][Pref clients: " + preferredClients + "][Disliked: " + dislikedStudents + "]";
        if (team != null)
            s += "[Project: " + team.getProject() + "]";

        return s;

    }

}

