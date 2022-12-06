package com.griddynamics.tech3camp.santa.claus.workshop.digression;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LabelMaker {
    private final Logger logger = LoggerFactory.getLogger(LabelMaker.class);

    private final Dictionary dictionary;
    private final String color;

    public LabelMaker(Dictionary dictionary, String color) {
        this.dictionary = dictionary;
        this.color = color;
    }

    public Label createLabel(String name) {
        return new Label(dictionary.translate(name), color);
    }
}
