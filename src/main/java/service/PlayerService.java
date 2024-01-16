package service;

import model.Player;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class PlayerService {

    private Map<Long, Player> players = new HashMap<>();
    private AtomicLong keySequence = new AtomicLong(1);

    public Player getPlayer(long playerId) {
        return players.get(playerId);
    }

    public Player getAnyPlayer() {
        if (players.isEmpty()) {
            return null;
        }
        long randomKey = new Random().nextLong(players.size());
        return players.get(randomKey + 1L);
    }

    public void addPlayer(String name, long clanId) {
        long id = keySequence.getAndIncrement();
        players.put(id, new Player(id, clanId, name, new AtomicInteger(0)));
    }
}
