package com.griddynamics.tech3camp.santa.claus.workshop.digression;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Glue {
    private final Logger logger = LoggerFactory.getLogger(Glue.class);

    public void glue() {
        logger.warn("Just gluing");
    }

    public void init() {
        logger.info("Preparing glue to work");
    }

    public void cleanUp() {
        logger.info("Destroying glue");
    }
}
