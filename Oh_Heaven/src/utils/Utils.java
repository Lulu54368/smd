package utils;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import java.util.Random;

public class Utils {
    private static Random random = new Random();
    public static Card randomCard(Hand hand){
        int x = random.nextInt(hand.getNumberOfCards());
        return hand.get(x);
    }
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
}
