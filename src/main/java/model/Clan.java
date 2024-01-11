package model;

import lombok.*;

import java.util.concurrent.atomic.AtomicInteger;

@Data
@AllArgsConstructor
@Builder
public class Clan {

    private long id;
    private String name;
    private AtomicInteger gold;
}
