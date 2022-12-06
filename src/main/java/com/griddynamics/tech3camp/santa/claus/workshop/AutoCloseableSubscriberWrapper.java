package com.griddynamics.tech3camp.santa.claus.workshop;

import com.google.cloud.pubsub.v1.Subscriber;

public class AutoCloseableSubscriberWrapper implements AutoCloseable {
    private final Subscriber subscriber;

    private final Object monitor = new Object();
    private boolean isWorkDone = false;

    public AutoCloseableSubscriberWrapper(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public void workDone() {
        synchronized (monitor) {
            isWorkDone = true;
            monitor.notifyAll();
        }
    }

    @Override
    public void close() throws Exception {
        subscriber.awaitRunning();
        synchronized (monitor) {
            while (!isWorkDone) {
                monitor.wait();
            }
        }
        subscriber.stopAsync();
        subscriber.awaitTerminated();
    }

    public void startAsync() {
        subscriber.startAsync();
    }
}
