package au.edu.rmit.projectmanager.model;

import au.edu.rmit.projectmanager.exceptions.RoleDoesNotExistException;
import au.edu.rmit.projectmanager.exceptions.TeamAlreadyFullException;
import au.edu.rmit.projectmanager.exceptions.UserDoesNotExistException;

import java.io.Serializable;
import java.util.ArrayList;

public class Team implements Serializable {

    private static final int MAX_CAPACITY = 4;

    private static final long serialVersionUID = 6529685078267756670L;

    private ArrayList<Student> students = new ArrayList<>();
    private double teamScore = 0;
    private Project project;

    public Team(Project project) {
        this.project = project;
        project.setTeam(this);
    }

    /**
     * Add student to the team
     *
     * @param student Student
     * @throws TeamAlreadyFullException The team is already full
     */
    public void addStudent(Student student) throws TeamAlreadyFullException {
        if (!this.isFull()) {
            this.students.add(student);
            student.setTeam(this);
        } else
            throw new TeamAlreadyFullException();
    }

    /**
     * Remove student from the team
     *
     * @param student Student
     * @throws UserDoesNotExistException The student is not part of the team
     */
    public void removeStudent(Student student) throws UserDoesNotExistException {
        if (this.students.contains(student)) {
            this.students.remove(student);
            if (student.getTeam() == this) {
                student.setTeam(null);
            }
        } else
            throw new UserDoesNotExistException();
    }

    /**
     * Calculate the total team score
     *
     * @return The team score
     */
    public double getTeamScore() {
        double score = 0;
        for (int i = 0; i < this.students.size(); i++) {
            score += getScoreForStudent(i);
        }
        this.teamScore = score;

        return score;
    }

    /**
     * Get the total GPA for the team
     *
     * @return The total GPA
     */
    public double getTotalGPA() {
        double totalGPA = 0;
        for (Student student : this.students) {
            totalGPA += student.getGpa();
        }

        return totalGPA;
    }

    /**
     * Check that team doesn't have more than one female
     *
     * @return Whether the team doesn't have more than one female
     */
    public boolean hasNoMoreThanOneFemaleMember() {
        int femaleMembers = 0;
        for (Student s : this.students) {
            if (s.getGender() == Types.Gender.Female) {
                femaleMembers++;
            }
        }

        return femaleMembers <= 1;
    }

    /**
     * Get score for a specific student
     *
     * @param i Index of the student
     * @return The student's score
     */
    private double getScoreForStudent(int i) {
        Student student = this.getStudentAtIndex(i);
        Role role;

        try {
            role = this.project.getRoleAtIndex(i);
        } catch (RoleDoesNotExistException ex) {
            System.out.println("Project " + project.getTitle() + " has no preferred roles.");
            role = null;
        }

        double score = 0;

        // Check if the project owner (Client) is in the preferred clients array
        // of the student
        for (Client client : student.getPreferredClients()) {
            if (client.getUsername().equals(this.project.getOwner())) {
                score += 2;
            }
        }

        // Check if disliked students in team:
        // 1. If first position: 0
        // 2. If second position: 1
        // 3. If third position: 2
        // 4. If not: 3
        ArrayList<Student> dislikedStudents = student.getDislikedStudents();
        double dislikedStudentScore = 0;
        for (Student teamMember : this.students) {
            for (int j = 0; j < dislikedStudents.size(); j++) {
                if (teamMember.username.equals(dislikedStudents.get(j).username)) {
                    dislikedStudentScore += j;
                } else {
                    dislikedStudentScore += 3;
                }
            }
        }
        score += dislikedStudentScore / 3;

        if (role != null) {

            // Check if the role fits the student
            ArrayList<Role> preferredRoles = student.getPreferredRoles();
            for (Role preferredRole : preferredRoles) {

                // If the name of the role matches, add 2 points
                if (preferredRole.getTitle().equals(role.getTitle())) {
                    score += 2;
                }

                // If any of the frameworks match, add 1 point
                ArrayList<String> frameworks = preferredRole.getFrameworks();
                for (String framework : frameworks) {
                    for (String roleFrameWork : role.getFrameworks()) {
                        if (framework.equals(roleFrameWork)) {
                            score += 1;
                        }
                    }
                }

                // If any of the languages match, add 1 point
                ArrayList<String> languages = role.getLanguages();
                for (String language : languages) {
                    for (String roleLanguage : role.getLanguages()) {
                        if (language.equals(roleLanguage)) {
                            score += 1;
                        }
                    }
                }


            }
        }

        return score;
    }

    /**
     * Get the average GPA of the team
     *
     * @return
     */
    public double getAverageGPA() {
        return getTotalGPA() / this.students.size();
    }

    /**
     * Get the student at a specific index
     *
     * @param i The index
     * @return Reference to the student
     */
    public Student getStudentAtIndex(int i) {
        return this.students.get(i);
    }

    /**
     * Whether the team is full
     * @return Whether the team is full
     */
    public boolean isFull() {
        return this.students.size() >= MAX_CAPACITY;
    }

    public ArrayList<Student> getStudents() {
        return this.students;
    }

    public int getTeamSize() {
        return this.students.size();
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        String string = "Project Title: " + project.getTitle() + "\n";
        string += "Team score: " + this.teamScore + "\n";
        for (Student s : this.students) {
            string += " - Student: " + s.getUsername() + "\n";
        }
        return string;
    }

}
