package service;

import model.Clan;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ClanService {

    private Map<Long, Clan> clans = new HashMap<>();
    private AtomicLong keySequence = new AtomicLong(1);

    public Clan getClan(long clanId){
        return clans.get(clanId);
    }

    public void addClan(String name){
        long id = keySequence.getAndIncrement();
        clans.put(id, new Clan(id, name, new AtomicInteger(0)));
    }

    public Clan getAnyClan(){
        if (clans.isEmpty()) {
            return null;
        }
        long randomKey = new Random().nextLong(clans.size());
        return clans.get(randomKey + 1L);
    }
}
