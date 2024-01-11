import lombok.extern.slf4j.Slf4j;
import model.Player;
import repository.H2ClanRepository;
import repository.H2TaskRepository;
import service.*;
import service.generators.ClanNameGenerator;
import service.generators.RandomNicknameGenerator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Slf4j
public class Main {

    private static H2TaskRepository taskRepository;
    private static H2ClanRepository clanRepository;
    private static List<Player> players = new ArrayList<>();
    private static ClanService clanService = new ClanService();
    private static TaskService taskService = new TaskService(clanService, 10, taskRepository);
    private static GoldService goldService = new GoldService(clanService, taskService);
    private static RandomNicknameGenerator nicknameGenerator = new RandomNicknameGenerator();
    private static ClanNameGenerator clanNameGenerator = new ClanNameGenerator();
    private static boolean isServerTernOn = true;

    public static void main(String[] args) {
        Random random = new Random();
        init();

        while (isServerTernOn){
            Player player = players.get(random.nextInt(players.size()));
            goldService.donateGoldToClan(player, player.getClanId(), random.nextInt(20));
            for
        }

    }

    public static void init() {

        long numberOfPlayers = 10;
        long numberOfClans = 2;

        try {
            taskRepository = new H2TaskRepository();
            clanRepository = new H2ClanRepository();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (long i = 1; i < numberOfClans + 1; i++) {
            clanService.addClan(clanNameGenerator.generateClanName());
        }

        Stream<Long> clanIds = clanService.getClanIds().stream();

        for (long i = 1; i < numberOfPlayers + 1; i++) {
            players.add((int) i, Player.builder()
                    .id(i)
                    .name(nicknameGenerator.generateRandomNickname())
                    .clanId(clanIds.findAny().orElseThrow(() -> new RuntimeException()))
                    .build());
        }
    }
}
