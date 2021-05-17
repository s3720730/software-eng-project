package au.edu.rmit.projectmanager;

import au.edu.rmit.projectmanager.exceptions.ProjectAlreadyExistsException;
import au.edu.rmit.projectmanager.exceptions.UserAlreadyExistsException;
import au.edu.rmit.projectmanager.model.App;
import au.edu.rmit.projectmanager.model.Role;
import au.edu.rmit.projectmanager.model.Types;
import au.edu.rmit.projectmanager.view.StartView;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        App app = new App();

        /*

        // Fake data population for testing purposes: TODO: delete eventually
        try {
            app.createUser("client1", "password1", "Johnny", Types.UserType.Client);
            app.createUser("client2", "password2", "Mike", Types.UserType.Client);
            app.createUser("admin", "admin","Boss", Types.UserType.Manager);
            app.createUser("student1", "password1","Richard", Types.UserType.Student);
            app.createUser("student2", "password2","Zoe", Types.UserType.Student);
        } catch (Exception e) {}

        //adding roles for testing purpose TODO: delete eventually
        ArrayList<String> frameworks = new ArrayList <String>();
        ArrayList<String> languages = new ArrayList <String>();
        frameworks.add("Django");
        languages.add("Java");
        languages.add("C++");
        Role r = new Role("Programmer", frameworks, languages);
        Role r2 = new Role("Leader", frameworks, languages);
        Role r3 = new Role("UI Designer", frameworks, languages);
        Role r4 = new Role("Software Tester", frameworks, languages);
        Role r5 = new Role("User Acceptance Tester", frameworks, languages);
        ArrayList<Role> roles = new ArrayList <>();
        roles.add(r);
        roles.add(r2);
        roles.add(r5);
        ArrayList<Role> roles2 = new ArrayList <>();
        roles.add(r);
        roles.add(r3);
        roles.add(r4);

        try{
            app.getAllClients().get(0).createProject("Johnny's first project", roles);
            app.getAllClients().get(0).createProject("Johnny's second project", null);
            app.getAllClients().get(1).createProject("Mike's first project", roles2);
        } catch(ProjectAlreadyExistsException e) {}

         */
        StartView startView = new StartView(app);
    }
}
