package com.griddynamics.tech3camp.santa.claus.workshop;


import java.io.IOException;

public class Workshop {
    private final AutoCloseableSubscriberWrapper ordersForGiftsSubscriber;
    private final SantaClaus santaClaus;

    public Workshop(
            AutoCloseableSubscriberWrapper ordersForGiftsSubscriber,
            SantaClaus santaClaus) {
        this.ordersForGiftsSubscriber = ordersForGiftsSubscriber;
        this.santaClaus = santaClaus;
    }

    public void prepareToWork() {
        santaClaus.start();
    }

    public void cleanAfterWork() throws InterruptedException {
        santaClaus.join();
    }
}
