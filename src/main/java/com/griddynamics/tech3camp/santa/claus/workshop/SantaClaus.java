package com.griddynamics.tech3camp.santa.claus.workshop;

import com.google.cloud.pubsub.v1.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.griddynamics.tech3camp.santa.claus.workshop.Utils.justSleepForShortRandomMoment;

public class SantaClaus extends Thread {

    Logger logger = LoggerFactory.getLogger(SantaClaus.class);
    private final Publisher requestGiftsForGoodChildrenPublisher;
    private final InputStream giftsListInputStream;
    private final MessageCreator reindeerMessageCreator;

    public SantaClaus(Publisher requestGiftsForGoodChildrenPublisher,
                      InputStream giftsListInputStream,
                      MessageCreator reindeerMessageCreator) {
        this.requestGiftsForGoodChildrenPublisher = requestGiftsForGoodChildrenPublisher;
        this.giftsListInputStream = giftsListInputStream;
        this.reindeerMessageCreator = reindeerMessageCreator;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(giftsListInputStream))) {
            br.lines().forEach(line -> {
                logger.debug("Gift '{}' request read", line);
                justSleepForShortRandomMoment();
                requestGiftsForGoodChildrenPublisher
                        .publish(reindeerMessageCreator.apply(line));
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            requestGiftsForGoodChildrenPublisher
                    .publish(reindeerMessageCreator.workFinishedMessage());
        }
    }
}
