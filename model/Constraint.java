package au.edu.rmit.projectmanager.model;

import java.io.Serializable;

public class Constraint implements Serializable {

    private String name;
    private double weight = 1;

    private static final long serialVersionUID = 3529685098267756670L;

    public Constraint(String name) {
        this.name = name;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "[Weight:" + weight + "][Constraint: " + name + "]";
    }
}
