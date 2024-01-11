package service;

import model.Task;
import repository.H2TaskRepository;

import java.time.LocalDateTime;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class TaskService {// какой-то сервис с заданиями

    private final H2TaskRepository repository;
    private final Executor executor;
    private final ClanService clans;
    private final AtomicLong taskCounter;

    public TaskService(ClanService clans, int nThreads, H2TaskRepository repository) {
        this.clans = clans;
        this.executor = Executors.newFixedThreadPool(nThreads);
        this.taskCounter = new AtomicLong(1);
        this.repository = repository;
    }

    private long getNewId() {
        return taskCounter.getAndIncrement();
    }

    void completeTask(Runnable task, String description, LocalDateTime createdAt) {
        Task task1 = Task.builder()
                .createdAt(createdAt)
                .description(description)
                .task(task)
                .build();

        executor.execute(() -> {
            task1.getTask().run();
            repository.save();
        });


    }
}
