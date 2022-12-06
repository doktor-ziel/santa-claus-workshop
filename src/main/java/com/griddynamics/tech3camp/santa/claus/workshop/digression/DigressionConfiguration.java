package com.griddynamics.tech3camp.santa.claus.workshop.digression;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@ConditionalOnProperty(value = "digression", havingValue = "true")
public class DigressionConfiguration {

    @Bean(initMethod = "init", destroyMethod = "cleanUp")
    @DependsOn("justGlue")
    public Dictionary polishDictionary() {
        return new Dictionary("/dictionary_pl.txt");
    }

    @Bean(initMethod = "init", destroyMethod = "cleanUp")
    public LabelMaker redPolishLabelMaker(Dictionary polishDictionary) {
        return new LabelMaker(polishDictionary, "red");
    }

    @Bean(initMethod = "init", destroyMethod = "cleanUp")
    public Glue justGlue() {
        return new Glue();
    }
}
