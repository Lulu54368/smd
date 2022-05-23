package player;

import ch.aplu.jcardgame.Deck;
import strategy.StrategyFactory;

public class NPC extends Player{
    StrategyFactory strategyFactory;
    String strategyName;
    public NPC(String strategy, Deck deck) {
        super(deck);
        strategyFactory = StrategyFactory.getInstance();
        strategyName = strategy;
    }

    public StrategyFactory getStrategyFactory() {
        return strategyFactory;
    }
}
