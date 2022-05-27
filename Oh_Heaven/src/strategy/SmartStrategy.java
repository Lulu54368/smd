package strategy;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.Round;
import player.Player;
import utils.Suit;

public class SmartStrategy implements IStrategy{

    public SmartStrategy() {
    }

    @Override
    public Card getNext(Player player, Round round) {

        if (player.getTrick() < player.getBid()){
            NotReachedBidStrategy NotReachedBid= new NotReachedBidStrategy();
            return NotReachedBid.selectCard(player, round);
        }else{
            ReachedBidStrategy ReachedBid = new ReachedBidStrategy();
            return ReachedBid.selectCard(player, round);
        }

    }
}
