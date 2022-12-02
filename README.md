# Santa Claus Workshop

## Introduction

This is project used during giving the talk _Why is it worth to know the bean's lifecycle?_ at **Tech3Camp #95 | JAVA** on December 6th, 2022.

The aim of this program is to present some potential threats we can face without deep understanding Spring beans' lifecycle.

## Running

If you want to run this project on your own you need to have the following software installed:
- JDK 17
- `gradle`
- `gcloud`

Before running the app you need to start the emulator of PubSub application. You can do that using the following command:

```bash
gcloud beta emulators pubsub start --project=santa-claus-project
```

In fact the `main` method defined in the `com.griddynamics.tech3camp.santa.claus.workshop.SantaClausWorkshopApplication` is irrelevant. The most important logic is implemented in the test method `presentationOfTheError` in `com.griddynamics.tech3camp.santa.claus.workshop.FailureScenarioTest` class. You can run it using IDE or using gradle wrapper:
```bash
./gradlew test --tests "com.griddynamics.tech3camp.santa.claus.workshop.FailureScenarioTest"
```

## Observations

The most important observation of this project is warning message visible after every test run:

```
2022-12-02T19:31:53.294+01:00  WARN 41093 --- [ionShutdownHook] o.s.b.f.support.DisposableBeanAdapter    : Custom destroy method 'shutdown' on bean with name 'requestGiftsForGoodChildrenPublisher' threw an exception: java.lang.IllegalStateException: Cannot shut down a publisher already shut-down.
```

Sometimes that can be visible different error message:
```
2022-12-02T18:33:31.786+01:00  WARN 33622 --- [          Gax-6] c.g.cloud.pubsub.v1.MessageDispatcher    : MessageReceiver failed to process ack ID: projects/santa-claus-project/subscriptions/santa-clause-subscription:343, the message will be nacked.

java.util.concurrent.RejectedExecutionException: null
	at java.base/java.util.concurrent.ForkJoinPool.externalPush(ForkJoinPool.java:2179) ~[na:na]
	at java.base/java.util.concurrent.ForkJoinPool.externalSubmit(ForkJoinPool.java:2196) ~[na:na]
	at java.base/java.util.concurrent.ForkJoinPool.submit(ForkJoinPool.java:2711) ~[na:na]
	at java.base/java.util.concurrent.ForkJoinPool.submit(ForkJoinPool.java:181) ~[na:na]
	at com.griddynamics.tech3camp.santa.claus.workshop.Reindeer.receiveMessage(Reindeer.java:26) ~[main/:na]
	at com.google.cloud.pubsub.v1.MessageDispatcher$3.run(MessageDispatcher.java:454) ~[google-cloud-pubsub-1.119.0.jar:1.119.0]
	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:539) ~[na:na]
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264) ~[na:na]
	at java.base/java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:304) ~[na:na]
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136) ~[na:na]
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635) ~[na:na]
	at java.base/java.lang.Thread.run(Thread.java:833) ~[na:na]
```

Worth to notice is also error visible in PubSub emulator's logs:
```
[pubsub] Dec 02, 2022 7:31:53 PM io.netty.channel.DefaultChannelPipeline onUnhandledInboundException
[pubsub] WARNING: An exceptionCaught() event was fired, and it reached at the tail of the pipeline. It usually means the last handler in the pipeline did not handle the exception.
[pubsub] java.io.IOException: Connection reset by peer
[pubsub]        at sun.nio.ch.FileDispatcherImpl.read0(Native Method)
[pubsub]        at sun.nio.ch.SocketDispatcher.read(SocketDispatcher.java:39)
[pubsub]        at sun.nio.ch.IOUtil.readIntoNativeBuffer(IOUtil.java:223)
[pubsub]        at sun.nio.ch.IOUtil.read(IOUtil.java:192)
[pubsub]        at sun.nio.ch.SocketChannelImpl.read(SocketChannelImpl.java:379)
[pubsub]        at io.netty.buffer.PooledByteBuf.setBytes(PooledByteBuf.java:253)
[pubsub]        at io.netty.buffer.AbstractByteBuf.writeBytes(AbstractByteBuf.java:1132)
[pubsub]        at io.netty.channel.socket.nio.NioSocketChannel.doReadBytes(NioSocketChannel.java:350)
[pubsub]        at io.netty.channel.nio.AbstractNioByteChannel$NioByteUnsafe.read(AbstractNioByteChannel.java:151)
[pubsub]        at io.netty.channel.nio.NioEventLoop.processSelectedKey(NioEventLoop.java:722)
[pubsub]        at io.netty.channel.nio.NioEventLoop.processSelectedKeysOptimized(NioEventLoop.java:658)
[pubsub]        at io.netty.channel.nio.NioEventLoop.processSelectedKeys(NioEventLoop.java:584)
[pubsub]        at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:496)
[pubsub]        at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:986)
[pubsub]        at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
[pubsub]        at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
[pubsub]        at java.lang.Thread.run(Thread.java:750)
[pubsub]
```