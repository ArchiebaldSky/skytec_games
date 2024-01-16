package service;

import lombok.RequiredArgsConstructor;
import model.Task;
import repository.H2TaskRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class TaskService {

    private final ExecutorService executor;
    private final AtomicLong keySequence;
    private final H2TaskRepository repository;

    public TaskService(int nThreads, H2TaskRepository repository) {
        this.executor = Executors.newFixedThreadPool(nThreads);
        this.keySequence = new AtomicLong(1);
        this.repository = repository;
    }

    private long getNewId() {
        return keySequence.getAndIncrement();
    }

    public void completeTask(Runnable task, String description) {
        long id = keySequence.getAndIncrement();
        LocalDateTime createdAt = LocalDateTime.now();
        Task taskModel = new Task(id, description, createdAt, null);
        executor.execute(() -> {
            task.run();
            taskModel.setCompletedAt(LocalDateTime.now());
            try {
                repository.save(taskModel);
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
