package service;

import lombok.RequiredArgsConstructor;
import model.Player;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
public class MobFarmingService { //Пример реализации патерна producer-consumer

    private final PlayerService playerService;
    private final GoldService goldService;
    private final Queue<Integer> enemiesOnLocation = new LinkedList<>();
    private TaskService producers = new TaskService(5);
    private TaskService consumers = new TaskService(5);
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private boolean isServerTernOn = true;
    private int maxSize = 10;
    private Random random = new Random();

    public void farm() {
        executor.execute(this::doWork);
    }

    public void finish() {
        isServerTernOn = false;
        producers.shutdownNow();
        consumers.shutdownNow();
    }

    private void doWork() {
        while (isServerTernOn) {
            String mobSpawnMessage = "Mob has spawned on location.";
            String mobKillMessage = "Mob has been killed.";
            producers.completeTask(this::spawnMob, mobSpawnMessage);
            consumers.completeTask(() -> huntMob(playerService.getAnyPlayer()), mobKillMessage);
        }
    }

    private void spawnMob() {
        synchronized (enemiesOnLocation) {
            while (isFullMob()) {
                try {
                    enemiesOnLocation.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            enemiesOnLocation.add(random.nextInt(10));
            enemiesOnLocation.notify();
        }
    }

    private void huntMob(Player player) {
        synchronized (enemiesOnLocation) {
            while (isNoMob()) {
                try {
                    enemiesOnLocation.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            Integer drop = enemiesOnLocation.poll();
            if (drop != null) {
                goldService.transferGoldToUser(player, drop, "drop");
            }
            enemiesOnLocation.notify();
        }
    }

    private boolean isNoMob() {
        return enemiesOnLocation.isEmpty();
    }

    private boolean isFullMob() {
        return enemiesOnLocation.size() >= maxSize;
    }
}
