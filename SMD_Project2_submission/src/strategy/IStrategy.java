package strategy;

import ch.aplu.jcardgame.Card;
import oh_heaven.game.Round;
import player.Player;

public interface IStrategy {
    Card getNext(Player player, Round round);
}
