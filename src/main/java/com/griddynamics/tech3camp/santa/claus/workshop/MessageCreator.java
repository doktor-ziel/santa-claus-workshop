package com.griddynamics.tech3camp.santa.claus.workshop;

import com.google.pubsub.v1.PubsubMessage;

import java.util.function.Function;

public class MessageCreator implements Function<String, PubsubMessage> {
    @Override
    public PubsubMessage apply(String giftName) {
        return PubsubMessage.newBuilder().putAttributes("gift", giftName).build();
    }

    public PubsubMessage workFinishedMessage() {
        return PubsubMessage.newBuilder().putAttributes("work", "done").build();
    }
}
