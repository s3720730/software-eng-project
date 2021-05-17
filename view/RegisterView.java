package au.edu.rmit.projectmanager.view;

import au.edu.rmit.projectmanager.exceptions.InvalidCredentialsException;
import au.edu.rmit.projectmanager.exceptions.InvalidUsernameException;
import au.edu.rmit.projectmanager.exceptions.UserAlreadyExistsException;
import au.edu.rmit.projectmanager.exceptions.UserDoesNotExistException;
import au.edu.rmit.projectmanager.model.App;
import au.edu.rmit.projectmanager.model.Client;
import au.edu.rmit.projectmanager.model.Types;
import au.edu.rmit.projectmanager.model.Types.UserType;
import au.edu.rmit.projectmanager.model.User;
//import sun.jvm.hotspot.asm.Register;

import java.util.Scanner;

public class RegisterView {

    public RegisterView(App app) {

        int choice = 0;
        Scanner intSc = new Scanner(System.in);
        Scanner strSc = new Scanner(System.in);

        Types.UserType userType = null;
        String name = "";
        String username = "";
        String password = "";
        String rePassword = "";

        System.out.println("================= Register ==================");
        System.out.println("What are you?");
        System.out.println("1 - Student");
        System.out.println("2 - Client");

        do {
            System.out.print("Your choice: ");
            choice = intSc.nextInt();
        } while (choice < 1 || choice > 2);


        switch (choice) {
            case 1:
                userType = UserType.Student;
                break;
            case 2:
                userType = UserType.Client;
        }

        do {
            System.out.print("Name: ");
            name = strSc.nextLine();
            if (!User.isInputValid(name))
                System.out.println("Invalid name. Try again.");
        } while (!User.isInputValid(name));

        do {
            System.out.print("Username: ");
            username = strSc.nextLine();
            if (!User.isInputValid(username))
                System.out.println("Invalid username. Try again.");
            if (!app.isUsernameUnique(username))
                System.out.println("Username already taken. Try again.");
        } while (!User.isInputValid(username) || !app.isUsernameUnique(username));

        do {
            System.out.print("Password: ");
            password = strSc.nextLine();
            if (!User.isInputValid(password))
                System.out.println("Invalid password. Try again.");
        } while (!User.isInputValid(password));

        do {
            System.out.print("Re-enter password: ");
            rePassword = strSc.nextLine();
            if (!User.doPasswordsMatch(password, rePassword))
                System.out.println("Passwords don't match. Try again.");
        } while (!User.doPasswordsMatch(password, rePassword));

        try {
            app.createUser(username, password, name, userType);
            System.out.println("Account registered!");
        } catch (UserAlreadyExistsException | InvalidUsernameException e) {
            System.out.println(e.getMessage());
        }

        try {
            User user = app.login(username, password);
            if (user instanceof Client)
                new ClientView(app);
            else
                new StudentView(app);
        } catch (UserDoesNotExistException | InvalidCredentialsException e) {
            System.out.println(e.getMessage());
        }
    }


}
