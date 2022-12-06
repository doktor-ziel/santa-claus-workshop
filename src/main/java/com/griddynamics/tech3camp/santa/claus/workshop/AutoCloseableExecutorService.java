package com.griddynamics.tech3camp.santa.claus.workshop;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class AutoCloseableExecutorService implements AutoCloseable, ExecutorService {
    private final ExecutorService adaptee;

    public AutoCloseableExecutorService(ExecutorService adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void close() throws Exception {
        adaptee.shutdown();
        adaptee.awaitTermination(30, TimeUnit.SECONDS);
    }

    @Override
    public void shutdown() {
        adaptee.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return adaptee.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return adaptee.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return adaptee.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return adaptee.awaitTermination(timeout, unit);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return adaptee.submit(task);
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return adaptee.submit(task, result);
    }

    @Override
    public Future<?> submit(Runnable task) {
        return adaptee.submit(task);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return adaptee.invokeAll(tasks);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return adaptee.invokeAll(tasks, timeout, unit);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return adaptee.invokeAny(tasks);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return adaptee.invokeAny(tasks, timeout, unit);
    }

    @Override
    public void execute(Runnable command) {
        adaptee.execute(command);
    }
}
