package com.griddynamics.tech3camp.santa.claus.workshop;

import com.griddynamics.tech3camp.santa.claus.workshop.digression.LabelMaker;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = { "digression=true" })
public class DigressionTest {

    public final Logger logger = LoggerFactory.getLogger(DigressionTest.class);

    @Autowired
    private LabelMaker labelMaker;

    @Test
    public void presentation() {
        logger.info("Created label: {}", labelMaker.createLabel("doll"));
    }
}
