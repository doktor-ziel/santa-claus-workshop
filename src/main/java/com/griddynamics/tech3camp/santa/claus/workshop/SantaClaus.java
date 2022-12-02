package com.griddynamics.tech3camp.santa.claus.workshop;

import com.google.cloud.pubsub.v1.Publisher;
import com.google.pubsub.v1.PubsubMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Function;

import static com.griddynamics.tech3camp.santa.claus.workshop.Utils.justSleepForRandomMoment;
import static com.griddynamics.tech3camp.santa.claus.workshop.Utils.justSleepForShortRandomMoment;

public class SantaClaus implements Runnable {

    Logger logger = LoggerFactory.getLogger(SantaClaus.class);
    private final Publisher publisher;
    private final InputStream inputStream;
    private final Function<String, PubsubMessage> reindeerMessageCreator;

    public SantaClaus(Publisher publisher, InputStream inputStream, Function<String, PubsubMessage> reindeerMessageCreator) {
        this.publisher = publisher;
        this.inputStream = inputStream;
        this.reindeerMessageCreator = reindeerMessageCreator;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            br.lines().forEach(line -> {
                logger.debug("Gift '{}' request read", line);
                justSleepForShortRandomMoment();
                publisher.publish(reindeerMessageCreator.apply(line));
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
