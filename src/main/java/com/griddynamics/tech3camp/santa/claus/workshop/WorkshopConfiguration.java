package com.griddynamics.tech3camp.santa.claus.workshop;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.rpc.TransportChannelProvider;
import com.google.cloud.pubsub.v1.*;
import com.google.pubsub.v1.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

@Configuration
public class WorkshopConfiguration {

    Logger logger = LoggerFactory.getLogger(WorkshopConfiguration.class);

    @Bean
    public InputStream giftsListInputStream(@Value("${gifts-list-resource-name}") String resourceName) {
        logger.error(resourceName);
        return getClass().getResourceAsStream(resourceName);
    }

    @Bean
    public ExecutorService elvesPool(@Value("${elves-pool-size}") int elvesNumber) {
        return Executors.newWorkStealingPool(elvesNumber);
    }

    @Bean
    public Publisher requestGiftsForGoodChildrenPublisher(
            TopicName topicName,
            TransportChannelProvider channelProvider,
            CredentialsProvider credentialsProvider) throws IOException {
        return Publisher.newBuilder(topicName)
                .setChannelProvider(channelProvider)
                .setCredentialsProvider(credentialsProvider)
                .build();
    }

    @Bean
    public MessageReceiver reindeerServant(ExecutorService elvesPool) {
        return new Reindeer(elvesPool);
    }

    @Bean
    @DependsOn("subscription")
    public Subscriber ordersForGiftsSubscriber(
            ProjectSubscriptionName projectSubscriptionName,
            MessageReceiver messageReceiver,
            TransportChannelProvider channelProvider,
            CredentialsProvider credentialsProvider) {
        return Subscriber.newBuilder(projectSubscriptionName, messageReceiver)
                .setChannelProvider(channelProvider)
                .setCredentialsProvider(credentialsProvider)
                .build();
    }

    @Bean
    public Function<String, PubsubMessage> reindeerMessageCreator() {
        return line -> PubsubMessage.newBuilder().putAttributes("gift", line).build();
    }

    @Bean
    public Workshop santaClausWorkshop(
            InputStream giftsListInputStream,
            ExecutorService elvesPool,
            Publisher requestGiftsForGoodChildrenPublisher,
            Subscriber ordersForGiftsSubscriber,
            Function<String, PubsubMessage> reindeerMessageCreator) {
        return new Workshop(
                giftsListInputStream, elvesPool, requestGiftsForGoodChildrenPublisher,
                ordersForGiftsSubscriber, reindeerMessageCreator);
    }
}