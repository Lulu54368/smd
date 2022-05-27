package strategy;

import ch.aplu.jcardgame.Card;
import oh_heaven.game.Round;
import player.Player;
import utils.Utils;

public class RandomStrategy implements IStrategy{

    @Override
    public Card getNext(Player player, Round round) {
        System.out.println("card" + Utils.randomCard(player.getHand()));
        return Utils.randomCard(player.getHand());
    }
}
