package com.griddynamics.tech3camp.santa.claus.workshop;

import com.google.cloud.pubsub.v1.Subscriber;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;

public class Workshop {
    private final InputStream giftsListInputStream;
    private final ExecutorService elvesPool;
    private final AutoCloseablePublisherWrapper requestGiftsForGoodChildrenPublisher;
    private final Subscriber ordersForGiftsSubscriber;
    private final SantaClaus santaClaus;

    public Workshop(
            InputStream giftsListInputStream,
            ExecutorService elvesPool,
            AutoCloseablePublisherWrapper requestGiftsForGoodChildrenPublisher,
            Subscriber ordersForGiftsSubscriber,
            SantaClaus santaClaus) {
        this.giftsListInputStream = giftsListInputStream;
        this.elvesPool = elvesPool;
        this.requestGiftsForGoodChildrenPublisher = requestGiftsForGoodChildrenPublisher;
        this.ordersForGiftsSubscriber = ordersForGiftsSubscriber;
        this.santaClaus = santaClaus;
    }

    public void prepareToWork() {
        ordersForGiftsSubscriber.startAsync();
        santaClaus.start();
    }

    public void cleanAfterWork() throws IOException, InterruptedException {
        santaClaus.join();
    }
}
