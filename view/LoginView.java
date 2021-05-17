package au.edu.rmit.projectmanager.view;

import au.edu.rmit.projectmanager.exceptions.InvalidCredentialsException;
import au.edu.rmit.projectmanager.exceptions.UserDoesNotExistException;
import au.edu.rmit.projectmanager.model.*;

import java.util.Scanner;

public class LoginView {


    public LoginView(App app) {

        Scanner in = new Scanner(System.in);

        System.out.println("================= Log In ==================");

        String username;
        String password;

        do {
            System.out.print("Username: ");
            username = in.next();
            System.out.print("Password: ");
            password = in.next();

            try {
                User user = app.login(username, password);

                if (user instanceof Manager) {
                    new ManagerView(app);
                } else if (user instanceof Client) {
                    new ClientView(app);
                } else {
                    new StudentView(app);
                }

            } catch (UserDoesNotExistException e) {
                System.out.println(e.getMessage());
                System.out.println("Please Try Again");
                new StartView(app);
            } catch (InvalidCredentialsException e) {
                System.out.println(e.getMessage());
                System.out.println("Please Try Again");
                new StartView(app);
            }

        } while (true);
    }
}
