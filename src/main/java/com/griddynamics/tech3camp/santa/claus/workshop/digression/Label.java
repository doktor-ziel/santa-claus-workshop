package com.griddynamics.tech3camp.santa.claus.workshop.digression;

public class Label {
    public final String name;
    public final String color;

    public Label(String name, String color) {
        this.name = name;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Label{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
