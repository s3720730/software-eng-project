package au.edu.rmit.projectmanager.view;

import au.edu.rmit.projectmanager.exceptions.TooManyPreferredRolesException;
import au.edu.rmit.projectmanager.exceptions.*;
import au.edu.rmit.projectmanager.model.*;

import java.util.ArrayList;
import java.util.Scanner;

public class StudentView {

    private App app;
    private Student student;

    public StudentView(App app) {
        Scanner scanner = new Scanner(System.in);
        String input;
        int choice;
        this.app = app;
        this.student = (Student) app.getCurrentUser();

        boolean loop = true;

        while (loop) {

            System.out.println("============ Student Dashboard ============");
            System.out.println("0 - Save and Exit");
            System.out.println("1 - Update profile details");
            System.out.println("2 - Add 4 preferred clients");
            System.out.println("3 - Add disliked students ");
            System.out.println("4 - Set Preferred Roles");
            if (app.isAllocationComplete()) {
                System.out.println("5 - View allocated team");
            }
            System.out.println("===========================================");
            System.out.print("Please enter the option number: ");
            while (!scanner.hasNextInt()) {
                input = scanner.next();
                System.out.println("Error: must enter a number.");
                System.out.print("Please enter the option number: ");
            }
            choice = scanner.nextInt();


            switch (choice) {
                case 0:
                    app.exit();
                    loop = false;
                    break;
                case 1:
                    updateProfileDetails();
                    break;
                case 2:
                    addPreferredClient();
                    break;
                case 3:
                    addDislikedStudent();
                    break;
                case 4:
                    setPreferredRoles();
                    break;
                case 5:
                    if (app.isAllocationComplete()) {
                        try {
                            viewAllocatedTeam();
                        } catch (TeamAlreadyFullException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                default:
                    System.out.println("Error: please a valid number");
            }
        }
    }

    private void updateProfileDetails() {

        Scanner scanner = new Scanner(System.in);
        boolean valid = false;
        String input = null;
        Types.Gender gender = null;
        double gpa = 0;
        int exp = 0;

        while (!valid) {
            System.out.print("Please enter your gender [M/F]: ");
            input = scanner.nextLine();

            if (input.equalsIgnoreCase("m") || input.equalsIgnoreCase("male")) {
                gender = Types.Gender.Male;
                valid = true;
            } else if (input.equalsIgnoreCase("f") || input.equalsIgnoreCase("female")) {
                gender = Types.Gender.Female;
                valid = true;
            } else {
                System.out.println("Error: must enter 'M' or 'F'.");
            }
        }

        valid = false;
        while (!valid) {
            System.out.print("Please enter your gpa: ");
            while (!scanner.hasNextDouble()) {
                input = scanner.next();
                System.out.println("Error: must enter a number.");
                System.out.print("Please enter your gpa: ");
            }
            gpa = scanner.nextDouble();
            if (gpa > 4 || gpa < 1) {
                System.out.println("Error: gpa out of bounds.");
            } else {
                valid = true;
            }
        }

        System.out.print("Please enter your years of experience: ");
        while (!scanner.hasNextInt()) {
            input = scanner.next();
            System.out.println("Error: must enter a number.");
            System.out.print("Please enter your years of experience: ");
        }
        exp = scanner.nextInt();

        try {
            student.updateProfile(gender, gpa, exp);
        } catch (GpaOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }

    }

    private void addPreferredClient() {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Client> clients = app.getAllClients();
        String script;
        String input;
        boolean valid = false;

        //Checking if preferred clients have already been added
        if (student.getPreferredClients().size() > 0) {
            System.out.print("You have already selected your preferred clients.\nDo you want to reselect your preferred clients [Y/N]: ");
            while (!valid) {
                input = scanner.nextLine();

                if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")) {
                    System.out.println("Deleting previously preferred clients.");
                    student.getPreferredClients().clear();
                    valid = true;
                } else if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")) {
                    return;
                } else {
                    System.out.println("Error: must enter yes or no.");
                }
            }
            valid = false;
        }

        for (int clientCount = 1; clientCount <= 4; clientCount++) {

            //Checking if enough clients exist to perform action
            if (clients.size() == 0) {
                System.out.println("Not enough clients to perform action.");
                return;
            }

            //Printing out all clients
            for (Client c : clients) {
                System.out.println("- " + c.toString());
            }

            if (clientCount == 1) {
                script = "st";
            } else if (clientCount == 2) {
                script = "nd";
            } else if (clientCount == 3) {
                script = "rd";
            } else {
                script = "th";
            }

            while (!valid) {
                System.out.print("Please enter the username of your " + clientCount + script + " preferred client: ");
                input = scanner.nextLine();

                for (Client c : clients) {
                    if (c.getUsername().equalsIgnoreCase(input)) {
                        try {
                            student.addPreferredClient(c);
                        } catch (PreferredClientAlreadyExistsException e) {
                            System.out.println(e.getMessage());
                        } catch (TooManyPreferredClientsException e) {
                            System.out.println(e.getMessage());
                        }
                        System.out.println("Client added");
                        clients.remove(c);
                        valid = true;
                        break;
                    }
                }
                if (!valid) {
                    System.out.println("Error: Client of that username does not exist.");
                }
            }
            valid = false;

        }

    }

    private void addDislikedStudent() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Student> students = app.getAllStudents();
        String script;
        String input;
        boolean valid = false;

        //Checking if disliked students have already been added
        if (student.getDislikedStudents().size() > 0) {
            System.out.println("You have already selected your disliked students.");
            while (!valid) {
                System.out.print("Do you want to reselect your disliked students [Y/N]: ");
                input = scanner.nextLine();
                if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")) {
                    System.out.println("Deleting previously disliked students.");
                    student.getDislikedStudents().clear();
                    valid = true;
                } else if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")) {
                    return;
                } else {
                    System.out.println("Error: must enter yes or no.");
                }
            }
            valid = false;
        }

        while (true) {

            //Checking if enough students exist to perform action
            if (students.size() == 0) {
                System.out.println("Not enough students to perform action.");
                return;
            }

            //Printing out all students
            for (Student s : students) {
                if (s != student)
                    System.out.println("- [Name: " + s.getName() + "][Username: " + s.getUsername() + "]");
            }

            if (student.getDislikedStudents().size() == 0) {
                script = "st";
            } else if (student.getDislikedStudents().size() == 1) {
                script = "nd";
            } else {
                script = "rd";
            }

            while (!valid) {
                System.out.print("Please enter the username of your " + (student.getDislikedStudents().size() + 1) + script + " disliked student: ");
                input = scanner.nextLine();
                for (Student s : students) {
                    if (s.getUsername().equalsIgnoreCase(input)) {
                        try {
                            student.addDislikedStudent(s);
                        } catch (DislikedStudentAlreadyExistsException e) {
                            System.out.println(e.getMessage());
                        } catch (AddingSelfAsDislikedStudentException e) {
                            System.out.println(e.getMessage());
                        } catch (TooManyDislikedStudentsException e) {
                            System.out.println(e.getMessage());
                        }
                        System.out.println("Disliked student added");
                        students.remove(s);
                        valid = true;
                        break;
                    }
                }
                if (!valid) {
                    System.out.println("Error: Student of that username does not exist.");
                }
            }
            valid = false;

            //checking if the student wants to add another disliked student
            if (student.getDislikedStudents().size() < 3) {
                while (!valid) {
                    System.out.print("Do you want to add another disliked student(maximum 3) [Y/N]: ");
                    input = scanner.nextLine();
                    if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")) {
                        valid = true;
                    } else if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")) {
                        return;
                    } else {
                        System.out.println("Error: must enter yes or no.");
                    }
                }
                valid = false;
            } else {
                return;
            }
        }
    }


    private void setPreferredRoles() {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Role> roles = app.getAllRoles();
        boolean valid = false;
        String script;
        String input;

        //Checking if preferred roles have already been added
        if (student.getPreferredRoles().size() > 0) {
            System.out.print("You have already selected your preferred roles.\nDo you want to reselect your preferred roles [Y/N]: ");
            while (!valid) {
                input = scanner.nextLine();
                if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")) {
                    System.out.println("Deleting previously selected roles.");
                    student.getPreferredRoles().clear();
                    valid = true;
                } else if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")) {
                    return;
                } else {
                    System.out.println("Error: must enter yes or no.");
                }
            }
            valid = false;
        }

        for (int roleCount = 1; roleCount <= 2; roleCount++) {

            for (Role r : roles) {
                System.out.println("- " + r.toString());
            }

            if (roles.size() == 0) {
                System.out.println("Not enough roles to perform action.");
                return;
            }


            if (roleCount == 1) {
                script = "st";
            } else {
                script = "nd";
            }


            while (!valid) {

                System.out.print("Please enter your " + roleCount + script + " preferred role: ");
                input = scanner.nextLine();

                for (Role r : roles) {
                    if (r.getTitle().equalsIgnoreCase(input)) {
                        try {
                            student.addPreferredRole(r);
                        } catch (PreferredRoleAlreadyExistsException e) {
                            System.out.println(e.getMessage());
                        } catch (TooManyPreferredRolesException e) {
                            System.out.println(e.getMessage());
                        }
                        ;
                        System.out.println("Role added");
                        roles.remove(r);
                        valid = true;
                        break;
                    }
                }
                if (!valid) {
                    System.out.println("Error: Role of that name does not exist.");
                }
            }
            valid = false;

        }

    }

    private void viewAllocatedTeam() throws TeamAlreadyFullException {
        System.out.println(student.getTeam().toString());
    }
}

