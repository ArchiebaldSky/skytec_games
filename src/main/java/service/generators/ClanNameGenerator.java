package service.generators;

import java.util.Random;

public class ClanNameGenerator {
    private final String[] ADJECTIVES = {"Mighty", "Brave", "Fierce", "Swift", "Savage"};
    private final String[] NOUNS = {"Dragons", "Wolves", "Storm", "Swords", "Shadows"};

    public String generateClanName() {
        Random random = new Random();
        String adjective = ADJECTIVES[random.nextInt(ADJECTIVES.length)];
        String noun = NOUNS[random.nextInt(NOUNS.length)];

        return adjective + " " + noun;
    }
}
