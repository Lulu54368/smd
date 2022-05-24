package player;

import ch.aplu.jcardgame.Deck;
import strategy.IStrategy;
import strategy.StrategyFactory;

public class NPC extends Player{
    StrategyFactory strategyFactory;
    String strategyName = "random"; //Have to set it here
    IStrategy iStrategy;
    public NPC(String strategy, Deck deck) {
        super(deck);
        strategyFactory = StrategyFactory.getInstance();
        strategyName = strategy;
        iStrategy = strategyFactory.getStrategy(strategyName);
    }

    public IStrategy getiStrategy() {
        return iStrategy;
    }
}
