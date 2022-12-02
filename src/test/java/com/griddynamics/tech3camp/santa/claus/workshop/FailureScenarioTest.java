package com.griddynamics.tech3camp.santa.claus.workshop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FailureScenarioTest {
    @Autowired
    private Workshop santaClausWorkshop;

    @Test
    void presentationOfTheError() throws Exception {
        santaClausWorkshop.prepareToWork();
        santaClausWorkshop.cleanAfterWork();
    }

}
