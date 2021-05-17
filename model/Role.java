package au.edu.rmit.projectmanager.model;

import au.edu.rmit.projectmanager.model.Types;

import java.io.Serializable;
import java.util.ArrayList;

public class Role implements Serializable {

    private static final long serialVersionUID = 6529684098267756670L;


    private String title;
    private ArrayList<String> frameworks;
    private ArrayList<String> languages;

    public Role(String title) {
        this.title = title;
    }

    public Role(String title, ArrayList<String> frameworks, ArrayList<String> languages) {
        this.title = title;
        this.frameworks = frameworks;
        this.languages = languages;
    }


    public ArrayList<String> getFrameworks() {
        return frameworks;
    }

    public ArrayList<String> getLanguages() {
        return languages;
    }


    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {

        String fw = "";
        if (frameworks == null) {
            fw += "NULL";
        } else {
            for (int i = 0; i < frameworks.size(); i++) {
                if (i == 0) {
                    fw += frameworks.get(i);
                } else {
                    fw += ", " + frameworks.get(i);
                }
            }
        }

        String l = "";
        if (languages == null) {
            l += "NULL";
        } else {
            for (int i = 0; i < languages.size(); i++) {
                if (i == 0) {
                    l += languages.get(i);
                } else {
                    l += ", " + languages.get(i);
                }
            }
        }

        return "[Role: " + title + "][Frameworks: " + fw + "][Languages: " + languages + "]";

    }

}
