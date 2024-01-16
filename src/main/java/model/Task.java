package model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class Task {

    private long id;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
}
