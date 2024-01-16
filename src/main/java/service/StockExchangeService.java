package service;

import lombok.RequiredArgsConstructor;
import model.Player;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RequiredArgsConstructor
public class StockExchangeService {

    private TaskService producers = new TaskService(5);
    private TaskService consumers = new TaskService(5);
    private final PlayerService playerService;
    private final GoldService goldService;
    private boolean isServerTernOn;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Random random = new Random();
    private BlockingQueue<Integer> stock = new LinkedBlockingDeque<>(5);
    private Lock lock = new ReentrantLock();

    public void doAuction() {
        executor.execute(() -> doWork());
    }

    public void finish() {
        isServerTernOn = false;
        producers.shutdownNow();
        consumers.shutdownNow();
    }

    public void doWork() {
        while (isServerTernOn){
            Player player = playerService.getAnyPlayer();
            String description = String.format("Player %s sale his loot.", player.getName());
            consumers.completeTask(() -> saleLootFromAuction(player), description);
        }
    }

    private void putUpLootForAuction() {
        boolean isLocked = lock.tryLock();
        if (isLocked) {
            for (int i = 0; i < random.nextInt(1, 10); i++) {
                int lootCost = random.nextInt(1, 7);
                try {
                    stock.put(lootCost);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void saleLootFromAuction(Player player) {
        if (stock.isEmpty()) {
            producers.completeTask(() -> putUpLootForAuction(), "Stock replenishment.");
        }
        int value;
        try {
            value = stock.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        goldService.transferGoldToUser(player, value, "stock");
    }
}
