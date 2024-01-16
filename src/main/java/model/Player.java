package model;

import lombok.*;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Player {

    private long id;
    private long clanId;
    private String name;
    private AtomicInteger gold;

    public void decrementGold(int value) {
        synchronized (this) {
            if (gold.get() >= value) {
                gold.getAndAdd(-value);
            }
        }
    }

    public void incrementGold(int value) {
        gold.getAndAdd(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id == player.id && clanId == player.clanId && name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clanId, name);
    }
}
