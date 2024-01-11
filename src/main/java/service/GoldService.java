package service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Clan;
import model.Player;

import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
public class GoldService {

    private final ClanService clans;

    private final TaskService taskService;

    public void donateGoldToClan(Player player, long clanId, int gold) {
        Clan clan = clans.getClan(clanId);
        taskService.completeTask();
    }

    public void transferGoldToUser(long authorId, long targetId, int gold){

    }
}
