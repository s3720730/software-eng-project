package au.edu.rmit.projectmanager.tests;

import au.edu.rmit.projectmanager.model.Role;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void createRole() {

        ArrayList<String> frameworks = new ArrayList<String>();
        ArrayList<String> languages = new ArrayList<String>();

        //creating role
        Role r = new Role("Coder", frameworks, languages);

        //testing role information is correct
        assertEquals("Coder", r.getTitle());
        assertEquals(frameworks, r.getFrameworks());
        assertEquals(languages, r.getLanguages());

    }

}