package au.edu.rmit.projectmanager.view;

import au.edu.rmit.projectmanager.exceptions.*;
import au.edu.rmit.projectmanager.model.*;

import java.util.ArrayList;
import java.util.Scanner;

public class ManagerView {

    App app;

    public ManagerView(App app) {

        Scanner in = new Scanner(System.in);
        int choice;
        this.app = app;

        do {
            System.out.println("============ Manager Dashboard ============");
            System.out.println("0 - Save and Exit");
            System.out.println("1 - View and Update Clients");
            System.out.println("2 - View and Update Projects");
            System.out.println("3 - View and Update Students (including personality types)");
            System.out.println("4 - Update GPA Threshold");
            System.out.println("5 - Specify Soft Constraints Weights");
            System.out.println("6 - Allocate Teams");
            if (app.isAllocationComplete()) {
                System.out.println("7 - View Generated Teams and Stats (after allocation)");
                System.out.println("8 - Swap Team Members (after allocation)");
            }
            System.out.println("===========================================");

            do {
                System.out.print("Please enter the option number: ");
                choice = in.nextInt();
            } while (((choice < 0 || choice > 8) && app.isAllocationComplete()) || ((choice < 0 || choice > 6) && !app.isAllocationComplete()));

            switch (choice) {
                case 1:
                    updateClients();
                    break;
                case 2:
                    updateProjects();
                    break;
                case 3:
                    updateStudents();
                    break;
                case 4:
                    updateGpaThreshold();
                    break;
                case 5:
                    specifyConstraintWeight();
                    break;
                case 6:
                    allocateTeams();
                    break;
                case 7:
                    viewStats();
                    break;
                case 8:
                    swapMembers();
                    break;
                case 0:
                    app.exit();
                    break;
            }
        } while (true);
    }

    private void updateClients() {
        int subChoice;
        Scanner in = new Scanner(System.in);
        ArrayList<Client> clients = app.getAllClients();
        for (int i = 0; i < clients.size(); i++)
            System.out.println((i + 1) + " - " + clients.get(i).toString());

        System.out.println("Enter client index to update him or 0 to return to Manager Dashboard");
        do {
            System.out.print("Your choice: ");
            subChoice = in.nextInt();
        } while (subChoice < 0 || subChoice > clients.size());

        if (subChoice == 0) {
            // Do nothing. Will return to Manager Dashboard
        } else {
            Client selectedClient = clients.get(subChoice - 1);
            System.out.println("Selected client: " + selectedClient.toString());
            System.out.println("What do you want to update?");
            System.out.println("1 - Name");
            System.out.println("2 - Password");
            System.out.println("3 - Delete client");
            System.out.println("4 - Nothing. Return to Manager Dashboard");

            do {
                System.out.print("Your choice: ");
                subChoice = in.nextInt();
            } while (subChoice < 1 || subChoice > 4);

            Scanner inString = new Scanner(System.in);

            switch (subChoice) {
                case 1:
                    System.out.print("Enter new name: ");
                    String name = inString.nextLine();
                    if (!name.equals("")) {
                        selectedClient.setName(name);
                        System.out.println("Name updated!");
                    } else
                        System.out.println("Invalid name. Nothing was updated.");
                    break;
                case 2:
                    System.out.print("Enter new password: ");
                    String password = inString.nextLine();
                    if (!password.equals("")) {
                        selectedClient.setPassword(password);
                        System.out.println("Password updated!");
                    } else
                        System.out.println("Invalid password. Nothing was updated.");
                    break;
                case 3:
                    try {
                        app.removeUser(selectedClient);
                        System.out.println("The client was deleted.");
                    } catch (UserDoesNotExistException e) {
                        System.out.println("The client could not be deleted. Try again.");
                    }
                case 4:
                    // Do nothing. Will return to Manager Dashboard
                    break;
            }
        }
    }

    private void updateProjects() {
        int subChoice;
        Scanner in = new Scanner(System.in);
        ArrayList<Project> projects = app.getAllProjects();
        for (int i = 0; i < projects.size(); i++)
            System.out.println((i + 1) + " - " + projects.get(i).toString());

        System.out.println("Enter project index to update it or 0 to return to Manager Dashboard");
        do {
            System.out.print("Your choice: ");
            subChoice = in.nextInt();
        } while (subChoice < 0 || subChoice > projects.size());

        if (subChoice == 0) {
            // Do nothing. Will return to Manager Dashboard
        } else {
            Project selectedProject = projects.get(subChoice - 1);
            System.out.println("Selected project: " + selectedProject.toString());
            System.out.println("What do you want to update?");
            System.out.println("1 - Title");
            System.out.println("2 - Cancel project");
            System.out.println("3 - Nothing. Return to Manager Dashboard");

            do {
                System.out.print("Your choice: ");
                subChoice = in.nextInt();
            } while (subChoice < 1 || subChoice > 3);

            switch (subChoice) {
                case 1:
                    Scanner inString = new Scanner(System.in);
                    System.out.print("Enter new title: ");
                    String title = inString.nextLine();
                    if (!title.equals("")) {
                        selectedProject.setTitle(title);
                        System.out.println("Title updated!");
                    } else
                        System.out.println("Invalid title. Nothing was updated.");
                    break;
                case 2:
                    selectedProject.setCancelled(true);
                    System.out.println("The project has been cancelled.");
                    break;
                case 3:
                    // Do nothing. Will return to Manager Dashboard
                    break;
            }
        }
    }

    private void updateStudents() {
        int subChoice;
        Scanner in = new Scanner(System.in);
        ArrayList<Student> students = app.getAllStudents();
        for (int i = 0; i < students.size(); i++)
            System.out.println((i + 1) + " - " + students.get(i).toString());

        System.out.println("Enter student index to update him or 0 to return to Manager Dashboard");
        do {
            System.out.print("Your choice: ");
            subChoice = in.nextInt();
        } while (subChoice < 0 || subChoice > students.size());

        if (subChoice == 0) {
            // Do nothing. Will return to Manager Dashboard
        } else {
            Student selectedStudent = students.get(subChoice - 1);
            System.out.println("Selected student: " + selectedStudent.toString());
            System.out.println("What do you want to update?");
            System.out.println("1 - Name");
            System.out.println("2 - GPA");
            System.out.println("3 - Gender");
            System.out.println("4 - Years of experience");
            System.out.println("5 - Personality type");
            System.out.println("6 - Password");
            System.out.println("7 - Delete student");
            System.out.println("8 - Nothing. Return to Manager Dashboard");

            do {
                System.out.print("Your choice: ");
                subChoice = in.nextInt();
            } while (subChoice < 1 || subChoice > 8);

            switch (subChoice) {
                case 1:
                    System.out.println("Current name is " + selectedStudent.getName());
                    Scanner inString = new Scanner(System.in);
                    System.out.print("Enter new name: ");
                    String name = inString.nextLine();
                    if (!name.equals("")) {
                        selectedStudent.setName(name);
                        System.out.println("Name updated!");
                    } else
                        System.out.println("Invalid name. Nothing was updated.");
                    break;
                case 2:
                    System.out.println("Current GPA is " + selectedStudent.getGpa());
                    Scanner inDouble = new Scanner(System.in);
                    double newGpa = 0.0;
                    do {
                        System.out.print("Enter new GPA between 0.1 and 4.0 (inclusively): ");
                        newGpa = inDouble.nextDouble();
                        try {
                            selectedStudent.setGpa(newGpa);
                        } catch (GpaOutOfBoundsException e) {
                            System.out.println(e.getMessage());
                        }
                    } while (newGpa < 0.1 || newGpa > 4.0);
                    System.out.println("GPA updated successfully. New GPA is " + selectedStudent.getGpa());
                    break;
                case 3:
                    System.out.println("Old gender is " + selectedStudent.getGender().toString());
                    if (selectedStudent.getGender() == Types.Gender.Male)
                        selectedStudent.setGender(Types.Gender.Female);
                    else
                        selectedStudent.setGender(Types.Gender.Male);
                    System.out.println(selectedStudent.getName() + " is now a " + selectedStudent.getGender().toString());
                    break;
                case 4:
                    System.out.println("Current years of experience: " + selectedStudent.getYearsOfExperience());
                    Scanner inInt = new Scanner(System.in);
                    int newExperience = 0;
                    do {
                        System.out.print("Enter new years of experience: ");
                        newExperience = inInt.nextInt();
                    } while (newExperience < 0 || newExperience > 100);
                    selectedStudent.setYearsOfExperience(newExperience);
                    System.out.println("Years of experience updated! New years of experience: " + selectedStudent.getYearsOfExperience());
                    break;
                case 5:
                    System.out.print("Enter personality type (A to F): ");
                    Scanner inSt = new Scanner(System.in);
                    String type = inSt.nextLine();
                    if (type.equals(""))
                        System.out.println("Invalid type. Nothing was updated.");
                    else {
                        if (type.equals("A"))
                            selectedStudent.setPersonalityType(Types.PersonalityType.A);
                        else if (type.equals("B"))
                            selectedStudent.setPersonalityType(Types.PersonalityType.B);
                        else if (type.equals("C"))
                            selectedStudent.setPersonalityType(Types.PersonalityType.C);
                        else if (type.equals("D"))
                            selectedStudent.setPersonalityType(Types.PersonalityType.D);
                        else if (type.equals("E"))
                            selectedStudent.setPersonalityType(Types.PersonalityType.E);
                        else
                            selectedStudent.setPersonalityType(Types.PersonalityType.F);
                        System.out.println("Personality type update. New personality type: " + selectedStudent.getPersonalityType());
                    }
                    break;
                case 6:
                    System.out.print("Enter new password: ");
                    Scanner inStr = new Scanner(System.in);
                    String password = inStr.nextLine();
                    if (!password.equals("")) {
                        selectedStudent.setPassword(password);
                        System.out.println("Password updated!");
                    } else
                        System.out.println("Invalid password. Nothing was updated.");
                    break;
                case 7:
                    try {
                        app.removeUser(selectedStudent);
                        System.out.println("The student was deleted.");
                    } catch (UserDoesNotExistException e) {
                        System.out.println("The student could not be deleted. Try again.");
                    }
                    break;
                case 8:
                    // Do nothing. Will return to Manager Dashboard
                    break;
            }
        }
    }

    private void updateGpaThreshold() {
        Scanner in = new Scanner(System.in);
        double newThreshold = 0.0;
        System.out.println("Current GPA Threshold is: " + app.getGpaThreshold());
        do {
            System.out.print("Enter a new value between 1.0 and 4.0 (with one decimal): ");
            newThreshold = in.nextDouble();
            try {
                app.setGpaThreshold(newThreshold);
            } catch (InvalidGpaThresholdException e) {
                System.out.println(e.getMessage());
            }
        } while (newThreshold < 1.0 || newThreshold > 4.0);

        System.out.println("GPA Threshold has been updated (new value = " + app.getGpaThreshold() + ")");
    }

    private void specifyConstraintWeight() {
        System.out.println("These are the current soft constraint weights:");
        ArrayList<Constraint> constraints = app.getConstraints();
        for (Constraint constraint : constraints) {
            System.out.println(constraint.toString());
        }

        Scanner in = new Scanner(System.in);
        double newWeight = 0;
        System.out.println("For each constraint, please specify a new value between 1.0 and 4.0 inclusively");
        for (Constraint constraint : constraints) {
            do {
                System.out.print("New weight for constraint \"" + constraint.getName() + "\": ");
                newWeight = in.nextDouble();
                try {
                    app.setConstraintWeight(constraint, newWeight);
                } catch (InvalidConstraintWeightException e) {
                    System.out.println(e.getMessage());
                }
            } while (newWeight < 1.0 || newWeight > 4.0);
        }

        System.out.println("All constraint weights updated.");
    }

    private void allocateTeams() {
        try {
            app.allocateTeams();
        } catch (TeamAlreadyFullException ex) {
            System.out.println("Something went wrong while allocating teams. Please try again later");
        }
    }

    private void viewStats() {
        Scanner in = new Scanner(System.in);
        System.out.println("\nPROJECT TEAMS");
        System.out.println("===========================================");
        for (Team t : app.getAllTeams()) {
            System.out.println(t.toString());
        }

        System.out.println("Press Enter");
        in.nextLine();
    }

    private void swapMembers() {
        Scanner in = new Scanner(System.in);
        Student student1;
        Student student2;

        System.out.println("Please enter the students that you want to swap below.");
        System.out.println("Student 1: ");

        try {
            student1 = app.getStudentByUsername(in.nextLine());

            System.out.println("Student 2: ");

            student2 = app.getStudentByUsername(in.nextLine());

            app.swapStudents(student1, student2);

            System.out.println("Swapped " + student1.getUsername() + " with " + student2.getUsername());

        } catch (UserDoesNotExistException ex) {
            System.out.println("This student does not exist.");
        } catch (TeamAlreadyFullException ex) {
            System.out.println("Could not swap students.");
        }
    }
}
