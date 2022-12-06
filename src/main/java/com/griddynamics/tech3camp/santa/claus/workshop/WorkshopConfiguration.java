package com.griddynamics.tech3camp.santa.claus.workshop;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.rpc.TransportChannelProvider;
import com.google.cloud.pubsub.v1.*;
import com.google.pubsub.v1.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@ConditionalOnProperty(value = "digression", havingValue = "false")
public class WorkshopConfiguration {
    @Bean
    public InputStream giftsListInputStream(
            @Value("${gifts-list-resource-name}") String resourceName) {
        return getClass().getResourceAsStream(resourceName);
    }

    @Bean
    public ExecutorService elvesPool(@Value("${elves-pool-size}") int elvesNumber) {
        return new AutoCloseableExecutorService(Executors.newWorkStealingPool(elvesNumber));
    }

    @Bean
    public SantaClaus santaClaus(
            AutoCloseablePublisherWrapper requestGiftsForGoodChildrenPublisher,
            InputStream giftsListInputStream,
            MessageCreator reindeerMessageCreator) {
        return new SantaClaus(
                requestGiftsForGoodChildrenPublisher,
                giftsListInputStream,
                reindeerMessageCreator);
    }

    @Bean
    @DependsOn("ordersForGiftsSubscriber")
    public AutoCloseablePublisherWrapper requestGiftsForGoodChildrenPublisher(
            TopicName topicName,
            TransportChannelProvider channelProvider,
            CredentialsProvider credentialsProvider) throws IOException {
        return new AutoCloseablePublisherWrapper(Publisher.newBuilder(topicName)
                .setChannelProvider(channelProvider)
                .setCredentialsProvider(credentialsProvider)
                .build());
    }

    @Bean
    public Reindeer reindeerServant(ExecutorService elvesPool) {
        return new Reindeer(elvesPool);
    }

    @Bean
    @DependsOn("subscription")
    public AutoCloseableSubscriberWrapper ordersForGiftsSubscriber(
            ProjectSubscriptionName projectSubscriptionName,
            Reindeer reindeer,
            TransportChannelProvider channelProvider,
            CredentialsProvider credentialsProvider) {
        AutoCloseableSubscriberWrapper subscriberWrapper = new AutoCloseableSubscriberWrapper(Subscriber.newBuilder(projectSubscriptionName, reindeer)
                .setChannelProvider(channelProvider)
                .setCredentialsProvider(credentialsProvider)
                .build());
        reindeer.setSubscriberWrapper(subscriberWrapper);
        return subscriberWrapper;
    }

    @Bean
    public MessageCreator reindeerMessageCreator() {
        return new MessageCreator();
    }

    @Bean
    public Workshop santaClausWorkshop(
            AutoCloseableSubscriberWrapper ordersForGiftsSubscriber,
            SantaClaus santaClaus) {
        return new Workshop(
                ordersForGiftsSubscriber, santaClaus);
    }
}
