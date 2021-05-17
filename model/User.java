package au.edu.rmit.projectmanager.model;

import java.util.UUID;
import java.io.Serializable;

public abstract class User implements Serializable {

    private static final long serialVersionUID = 6529685098267256670L;

    protected String id;
    protected String name;
    protected String username;
    protected String password;

    protected User(String name, String username, String password) {
        this.id = generateId();
        this.name = name;
        this.username = username;
        this.password = password;
    }

    protected User(String username, String password) {
        this.id = generateId();
        this.username = username;
        this.password = password;
    }

    /**
     * Check that user-related input is valid
     *
     * @param input The input to verify (usually username, password or name)
     * @return Whether the input is valid
     */
    public static boolean isInputValid(String input) {
        if (input.isEmpty() || input.equals("") || input.charAt(0) == ' ')
            return false;
        else
            return true;
    }

    /**
     * Check that 2 passwords match
     *
     * @param p1 First password
     * @param p2 Second password
     * @return Whether the 2 passwords match
     */
    public static boolean doPasswordsMatch(String p1, String p2) {
        return p1.equals(p2);
    }

    public static boolean doUsernamesMatch(String u1, String u2) { return u1.equals(u2); }

    public String getName() {
        return this.name;
    }

    public String getUsername() {
        return this.username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "[Name: " + name + "][Username: " + username + "][Password: " + password + "]";
    }

}
