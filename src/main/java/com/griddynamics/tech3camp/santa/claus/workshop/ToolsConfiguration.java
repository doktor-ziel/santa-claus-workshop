package com.griddynamics.tech3camp.santa.claus.workshop;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.grpc.GrpcTransportChannel;
import com.google.api.gax.rpc.ApiException;
import com.google.api.gax.rpc.FixedTransportChannelProvider;
import com.google.api.gax.rpc.TransportChannelProvider;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminSettings;
import com.google.pubsub.v1.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.io.IOException;

@Configuration
public class ToolsConfiguration {
    @Bean
    public CredentialsProvider credentialsProvider() {
        return NoCredentialsProvider.create();
    }

    @Bean
    public ManagedChannel managedChannel(@Value("${gcp-pubsub-host-port}") String hostPort) {
        return ManagedChannelBuilder.forTarget(hostPort).usePlaintext().build();
    }

    @Bean
    public TransportChannelProvider channelProvider(ManagedChannel managedChannel) {
        return FixedTransportChannelProvider.create(GrpcTransportChannel.create(managedChannel));
    }

    @Bean
    public TopicName topicName(
            @Value("${gcp-project-name}") String projectName,
            @Value("${gcp-notification-topic}") String notificationTopic) {
        return TopicName.of(projectName, notificationTopic);
    }


    @Bean
    public SubscriptionName subscriptionName(@Value("${gcp-project-name}") String projectName,
                                             @Value("${gcp-subscription-name}") String subscriptionName) {
        return SubscriptionName.of(projectName, subscriptionName);
    }

    @Bean
    public ProjectSubscriptionName projectSubscriptionName(
            @Value("${gcp-project-name}") String projectName,
            @Value("${gcp-subscription-name}") String subscriptionName) {
        return ProjectSubscriptionName.of(projectName, subscriptionName);
    }

    @Bean
    public TopicAdminClient topicAdminClient(
            TransportChannelProvider channelProvider,
            CredentialsProvider credentialsProvider) throws IOException {
        return TopicAdminClient.create(
                TopicAdminSettings.newBuilder()
                        .setTransportChannelProvider(channelProvider)
                        .setCredentialsProvider(credentialsProvider)
                        .build());
    }

    @Bean
    public Topic topic(TopicAdminClient topicAdminClient, TopicName topicName) {
        try {
            return topicAdminClient.getTopic(topicName);
        } catch (ApiException e) {
            return topicAdminClient.createTopic(topicName);
        }
    }

    @Bean
    public SubscriptionAdminClient subscriptionAdminClient(
            TransportChannelProvider channelProvider,
            CredentialsProvider credentialsProvider) throws IOException {
        return SubscriptionAdminClient.create(
                SubscriptionAdminSettings.newBuilder()
                        .setTransportChannelProvider(channelProvider)
                        .setCredentialsProvider(credentialsProvider)
                        .build());
    }

    @Bean
    @DependsOn("topic")
    public Subscription subscription(
            SubscriptionAdminClient subscriptionAdminClient,
            SubscriptionName subscriptionName,
            TopicName topicName) {
        try {
            return subscriptionAdminClient.getSubscription(subscriptionName);
        } catch (ApiException e) {
            return subscriptionAdminClient.createSubscription(
                    subscriptionName, topicName, PushConfig.getDefaultInstance(), 1);
        }
    }
}
