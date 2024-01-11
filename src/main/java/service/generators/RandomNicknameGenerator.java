package service.generators;

import java.util.Random;

public class RandomNicknameGenerator {
    private final String[] PREFIXES = {"Cool", "Awesome", "Epic", "Super", "Mega"};
    private final String[] SUFFIXES = {"Player", "Gamer", "Master", "Ninja", "Pro"};

    public String generateRandomNickname() {
        Random random = new Random();
        String prefix = PREFIXES[random.nextInt(PREFIXES.length)];
        String suffix = SUFFIXES[random.nextInt(SUFFIXES.length)];
        int randomNumber = 1000 + random.nextInt(9000); // Генерация случайного трехзначного числа

        return String.format("%s_%s_%d", prefix, suffix, randomNumber);
    }
}
