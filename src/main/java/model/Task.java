package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Task {

    private long id;
    private Runnable task;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private int TTL; //Time to live (hours)
}
