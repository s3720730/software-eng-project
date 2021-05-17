package au.edu.rmit.projectmanager.tests;

import au.edu.rmit.projectmanager.model.User;
import au.edu.rmit.projectmanager.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User user;

    @BeforeEach
    void setUp() {
        user = new Client("testUser", "test@email.com", "testpassword");
    }

    @Test
    void isNameValid() {
        String name = "";
        assertFalse(User.isInputValid(name));
        name = " ";
        assertFalse(User.isInputValid(name));
        name = "valid";
        assertTrue(User.isInputValid(name));
    }

}