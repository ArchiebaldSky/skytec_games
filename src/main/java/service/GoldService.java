package service;

import lombok.RequiredArgsConstructor;
import model.Clan;
import model.Player;
import repository.H2TaskRepository;

@RequiredArgsConstructor
public class GoldService {

    private final ClanService clanService;
    private final PlayerService playerService;
    private TaskService taskService = new TaskService(10, new H2TaskRepository());

    public void donateGoldToClan(Player player, int gold) {
        Clan clan = clanService.getClan(player.getClanId());
        String description = String.format("Gold transaction from player %s to clan %s for %d gold.", player.getName(), clan.getName(), gold);
        taskService.completeTask(() -> doTransaction(player, clan, gold), description);
    }

    public void transferGoldToUser(Player player, int gold, String source) {
        String description = String.format("Player %s get %d gold from %s.", player.getName(), gold, source);
        taskService.completeTask(() -> doTransaction(player, gold), description);
    }

    public void transferGoldFromUserToUser(Player author, Player target, int gold) {
        String description = String.format("Gold transaction from player %s to player %s for %d gold.", author.getName(), target.getName(), gold);
        taskService.completeTask(() -> doTransaction(author, target, gold), description);
    }

    public void doTransaction(Player player, Clan clan, int gold) {
        if (player.getGold().get() > gold) {
            player.decrementGold(gold);
            clan.incrementGold(gold);
        } else {
            System.out.println("Not enough gold.");
        }
    }

    public void doTransaction(Player player, int gold) {
        player.incrementGold(gold);
    }

    public void doTransaction(Player author, Player target, int gold) {
        if (author.getGold().get() > gold) {
            author.decrementGold(gold);
            target.incrementGold(gold);
        } else {
            System.out.println("Not enough gold.");
        }
    }
}
