package com.griddynamics.tech3camp.santa.claus.workshop;

import com.google.cloud.pubsub.v1.Publisher;
import com.google.pubsub.v1.PubsubMessage;

import java.util.concurrent.TimeUnit;

public class AutoCloseablePublisherWrapper implements AutoCloseable {
    private final Publisher publisher;

    public AutoCloseablePublisherWrapper(Publisher publisher) {
        this.publisher = publisher;
    }


    @Override
    public void close() throws Exception {
        publisher.shutdown();
        publisher.awaitTermination(30, TimeUnit.SECONDS);
    }

    public void publish(PubsubMessage pubsubMessage) {
        publisher.publish(pubsubMessage);
    }
}
