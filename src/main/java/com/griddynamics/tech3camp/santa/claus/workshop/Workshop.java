package com.griddynamics.tech3camp.santa.claus.workshop;

import com.google.cloud.pubsub.v1.Publisher;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.PubsubMessage;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;

public class Workshop {
    private final InputStream giftsListInputStream;
    private final ExecutorService elvesPool;
    private final Publisher requestGiftsForGoodChildrenPublisher;
    private final Subscriber ordersForGiftsSubscriber;
    private final Thread santaClaus;

    public Workshop(
            InputStream giftsListInputStream,
            ExecutorService elvesPool,
            Publisher requestGiftsForGoodChildrenPublisher,
            Subscriber ordersForGiftsSubscriber,
            Function<String, PubsubMessage> reindeerMessageCreator) {
        this.giftsListInputStream = giftsListInputStream;
        this.elvesPool = elvesPool;
        this.requestGiftsForGoodChildrenPublisher = requestGiftsForGoodChildrenPublisher;
        this.ordersForGiftsSubscriber = ordersForGiftsSubscriber;
        this.santaClaus = new Thread(new SantaClaus(requestGiftsForGoodChildrenPublisher, giftsListInputStream, reindeerMessageCreator));
    }

    public void prepareToWork() {
        ordersForGiftsSubscriber.startAsync();
        santaClaus.start();
    }

    public void cleanAfterWork() throws IOException, InterruptedException {
        santaClaus.join();
        elvesPool.shutdown();
        ordersForGiftsSubscriber.stopAsync();
        requestGiftsForGoodChildrenPublisher.shutdown();
        giftsListInputStream.close();
    }
}
