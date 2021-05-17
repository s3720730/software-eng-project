package au.edu.rmit.projectmanager.view;

import au.edu.rmit.projectmanager.exceptions.ProjectAlreadyExistsException;
import au.edu.rmit.projectmanager.exceptions.ProjectDoesNotExistException;
import au.edu.rmit.projectmanager.model.App;
import au.edu.rmit.projectmanager.model.Client;
import au.edu.rmit.projectmanager.model.Project;
import au.edu.rmit.projectmanager.model.Role;

import java.util.ArrayList;
import java.util.Scanner;

public class ClientView {

    private App app;
    private Client client;
    private ArrayList<Role> roles = new ArrayList<Role>();

    public ClientView(App app) {

        Scanner scanner = new Scanner(System.in);
        String input;
        int choice;
        this.app = app;
        this.client = (Client) app.getCurrentUser();

        boolean loop = true;

        while (loop) {
            System.out.println("============ Client Dashboard ============");
            System.out.println("0 - Save and Exit");
            System.out.println("1 - Add Project");
            System.out.println("2 - Delete Project");
            System.out.println("3 - View My Projects");
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
                    loop = false;
                    app.exit();
                    break;
                case 1:
                    addProject();
                    break;
                case 2:
                    deleteProject();
                    break;
                case 3:
                    viewProjects();
                    break;
                default:
                    System.out.println("Error: must enter a valid number.");
            }


        }
    }

    private void addProject() {

        Scanner in = new Scanner(System.in);
        ArrayList<String> frameworks = new ArrayList<String>();
        ArrayList<String> languages = new ArrayList<String>();
        String input;
        String title = null;
        boolean valid = false;
        int fw = 0;
        int l = 0;

        while (!valid) {

            System.out.print("Please enter the title of your project: ");
            title = in.nextLine();
            try {
                valid = client.createProject(title);
                roles = new ArrayList<>();
               // valid = true;
            } catch (ProjectAlreadyExistsException e) {

                System.out.println(e.getMessage());

            }
        }


        for (int x = 0; x < 4; x++) {

            String script;
            if (x == 0) {
                script = "st";
            } else if (x == 1) {
                script = "nd";
            } else if (x == 2) {
                script = "rd";
            } else {
                script = "th";
            }

            System.out.print("Please enter the title of your " + (x + 1) + script + " role: ");
            String roleName = in.nextLine();

            valid = false;
            while (!valid) {
                System.out.print("Please enter the number of frameworks you want your role to be familiar with: ");
                input = in.nextLine();
                try {
                    fw = Integer.parseInt(input);
                    valid = true;
                } catch (NumberFormatException e) {
                    System.out.println("Error: must enter a number.");
                }
            }

            valid = false;
            while (!valid) {
                System.out.print("Please enter the number of languages you want your role to be familiar with: ");
                input = in.nextLine();
                try {
                    l = Integer.parseInt(input);
                    valid = true;
                } catch (NumberFormatException e) {
                    System.out.println("Error: must enter a number.");
                }
            }


            for (int y = 0; y < fw; y++) {

                System.out.print("Please enter a framework for this role: ");
                input = in.nextLine();
                frameworks.add(input);
            }

            for (int y = 0; y < l; y++) {

                System.out.print("Please enter a language for this role: ");
                input = in.nextLine();
                languages.add(input);
            }

            Role r = new Role(roleName, frameworks, languages);
            roles.add(r);
            System.out.println("Role created");
        }

        try {
            client.setProjectRoles(title, roles);
            System.out.println("Project created");
        } catch (ProjectDoesNotExistException e) {
            System.out.println(e.getMessage());
        }


    }

    private void deleteProject() {

        Scanner in = new Scanner(System.in);
        boolean loop = true;

        while (loop) {

            System.out.print("Please enter the title of the project you wish to delete: ");
            String input = in.nextLine();

            try {
                Project p = client.getProject(input);
                client.deleteProject(p.getTitle());
                System.out.println("Project Deleted");
                loop = false;
            } catch (ProjectDoesNotExistException e) {
                System.out.println(e.getMessage());

            }
        }


    }

    private void viewProjects() {

        String string = "";

        for (Project p : client.getProjects()) {
            string += p.toString() + "\n";
        }

        System.out.println(string);
    }
}
