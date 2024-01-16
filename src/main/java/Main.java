import model.Player;
import service.*;
import service.generators.ClanNameGenerator;
import service.generators.RandomNicknameGenerator;
import java.util.Random;

public class Main {

    private static PlayerService playerService = new PlayerService();
    private static ClanService clanService = new ClanService();
    private static GoldService goldService = new GoldService(clanService, playerService);
    private static MobFarmingService mobFarmingService = new MobFarmingService(playerService, goldService);
    private static StockExchangeService stockExchangeService = new StockExchangeService(playerService, goldService);
    private static RandomNicknameGenerator nicknameGenerator = new RandomNicknameGenerator();
    private static ClanNameGenerator clanNameGenerator = new ClanNameGenerator();
    private static Random random = new Random();

    public static void main(String[] args) {

        init();

        Thread game = new Thread(() -> {
            while (true) {
                Player player = playerService.getAnyPlayer();
                int gold = player.getGold().get();
                if (gold > 1) {
                    goldService.donateGoldToClan(player, random.nextInt(1, gold));
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        mobFarmingService.farm();
        stockExchangeService.doAuction();
        game.start();

        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.exit(0);
    }

    public static void init() {
        long numberOfPlayers = 10;
        long numberOfClans = 2;

        for (long i = 1; i < numberOfClans + 1; i++) {
            clanService.addClan(clanNameGenerator.generateClanName());
        }

        for (long i = 1; i < numberOfPlayers + 1; i++) {
            playerService.addPlayer(nicknameGenerator.generateRandomNickname(), clanService.getAnyClan().getId());
        }
    }
}
