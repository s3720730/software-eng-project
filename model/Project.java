package au.edu.rmit.projectmanager.model;

import au.edu.rmit.projectmanager.exceptions.RoleDoesNotExistException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Project implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;

    private String title;
    private ArrayList<Role> requiredRoles;
    private Team team = null;
    private Client owner;
    boolean isCancelled = false;
    int satisfactionScore;

    public Project(Client owner, String title, ArrayList<Role> requiredRoles) {
        this.title = title;
        this.requiredRoles = requiredRoles;
        this.owner = owner;
    }

    public Project(Client owner, String title) {
        this.owner = owner;
        this.title = title;
    }

    /**
     * Return role at a specific index
     *
     * @param i The index
     * @return Reference to the role
     * @throws RoleDoesNotExistException The role does not exist in the system
     */
    public Role getRoleAtIndex(int i) throws RoleDoesNotExistException {
        try {
            return requiredRoles.get(i);
        } catch (NullPointerException ex) {
            throw new RoleDoesNotExistException();
        }
    }

    public String getTitle() {
        return title;
    }

    public Client getOwner() {
        return this.owner;
    }

    public Team getTeam() {
        return this.team;
    }

    public ArrayList<Role> getRoles() {
        return this.requiredRoles;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRoles(ArrayList<Role> roles) {
        this.requiredRoles = roles;
    }

    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    @Override
    public String toString() {

        String roles = "";

        if (requiredRoles != null) {
            for (int i = 0; i < requiredRoles.size(); i++) {
                if (i == 0) {
                    roles += requiredRoles.get(i).getTitle();
                } else {
                    roles += ", " + requiredRoles.get(i).getTitle();
                }
            }
        }

        String members = "";
        if (team == null) {
            members += "NULL";
        } else {
            for (int i = 0; i < team.getTeamSize(); i++) {
                if (i == 0) {
                    members += team.getStudents().get(i).getName();
                } else {
                    members += ", " + team.getStudents().get(i).getName();
                }
            }
        }

        return "[Title: " + title + "][Cancelled: " + isCancelled + "][Score: " + satisfactionScore + "]" +
                "[Roles: " + roles + "][Members: " + members + "]";
    }

}
