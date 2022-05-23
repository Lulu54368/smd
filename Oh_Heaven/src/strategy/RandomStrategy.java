package strategy;

import ch.aplu.jcardgame.Card;
import oh_heaven.game.Round;
import player.Player;
import utils.Utils;

public class RandomStrategy implements IStrategy{
    private Utils utils = new Utils();
    @Override
    public Card getNext(Player player, Round round) {
        return utils.randomCard(player.getHand());
    }
}
