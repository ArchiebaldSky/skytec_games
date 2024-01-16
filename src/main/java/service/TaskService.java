package service;

import model.Task;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class TaskService {

    private final ExecutorService executor;
    private final AtomicLong keySequence;

    public TaskService(int nThreads) {
        this.executor = Executors.newFixedThreadPool(nThreads);
        this.keySequence = new AtomicLong(1);
    }

    public void completeTask(Runnable task, String description) {
        long id = keySequence.getAndIncrement();
        LocalDateTime createdAt = LocalDateTime.now();
        Task taskModel = new Task(id, description, createdAt, null);
        executor.execute(() -> {
            task.run();
            taskModel.setCompletedAt(LocalDateTime.now());
            System.out.println(taskModel);
        });
    }

    public void awaitTermination(long timeout, TimeUnit unit) {
        try {
            executor.awaitTermination(timeout, unit);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void shutdownNow() {
        executor.shutdownNow();
    }
}
