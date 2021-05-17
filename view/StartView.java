package au.edu.rmit.projectmanager.view;

import au.edu.rmit.projectmanager.model.App;

import java.util.Scanner;


public class StartView {

    public StartView(App app) {

        Scanner in = new Scanner(System.in);
        int choice = 0;

        do {
            System.out.println("===========================================");
            System.out.println("       Welcome to The Project Manager      ");
            System.out.println("===========================================");
            System.out.println("1 - Login");
            System.out.println("2 - Register");
            System.out.println("3 - Save and Exit");
            System.out.println("===========================================");

            do {
                System.out.print("Please enter the option number: ");
                choice = in.nextInt();
            } while (choice < 1 || choice > 3);

            switch (choice) {
                case 1:
                    LoginView loginView = new LoginView(app);
                    break;
                case 2:
                    RegisterView registerView = new RegisterView(app);
                    break;
                case 3:
                    app.exit();
                    break;
            }
        } while (true);
    }
}
