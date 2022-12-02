package com.griddynamics.tech3camp.santa.claus.workshop;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.pubsub.v1.PubsubMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

import static com.griddynamics.tech3camp.santa.claus.workshop.Utils.*;

public class Reindeer implements MessageReceiver {

    Logger logger = LoggerFactory.getLogger(Reindeer.class);
    private final ExecutorService elvesPool;

    public Reindeer(ExecutorService elvesPool) {
        this.elvesPool = elvesPool;
    }

    @Override
    public void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {
        justSleepForLongRandomMoment();
        String gift = message.getAttributesOrDefault("gift", "surprise gift");
        elvesPool.submit(new GiftProductionTask(gift));
        logger.debug("Production of the gift '{}' delegated to elves", gift);
        consumer.ack();
    }
}
