package service;

import lombok.extern.slf4j.Slf4j;
import model.Clan;
import repository.H2ClanRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ClanService {

    private H2ClanRepository repository;

    public Clan getClan(long clanId){
        Clan clan = null;
        try {
            clan = repository.getById(clanId).orElseThrow(() -> new RuntimeException());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clan;
    }

    public void addClan(String name){
        try {
            repository.addClan(name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Long> getClanIds(){
        List<Long> result = new ArrayList<>();

        try {
            result = repository.getClanIds();
        } catch (SQLException e) {
            log.error(String.valueOf(e));
        }
        return result;
    }

    public void compareAndSet(int gold, int current){

    }
}
