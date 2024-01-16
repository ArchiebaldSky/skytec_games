package model;

import lombok.*;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Clan {

    private long id;
    private String name;
    private AtomicInteger gold;

    public void decrementGold(int value) {
        synchronized (this) {
            if (gold.get() >= value) {
                gold.getAndAdd(-value);
            }
        }
    }

    public void incrementGold(int value){
        gold.getAndAdd(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clan clan = (Clan) o;
        return id == clan.id && name.equals(clan.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
