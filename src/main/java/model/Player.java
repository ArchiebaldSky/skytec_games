package model;

import lombok.*;

import java.util.concurrent.atomic.AtomicInteger;

@Data
@AllArgsConstructor
@Builder
public class Player {

    private long id;
    private long clanId;
    private String name;
}
