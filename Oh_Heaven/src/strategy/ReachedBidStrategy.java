package strategy;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.Round;
import player.Player;
import utils.Suit;

import java.util.ArrayList;

public class ReachedBidStrategy {

    public Card selectCard(Player player, Round round){

        Suit leadSuit = round.getLead();
        Suit trumpSuit = round.getTrump();
        Card winning = round.getWinningCard();
        Hand currentHand = player.getHand();

        // play the smallest as first player
        if(leadSuit == null){
            return findSmallest(currentHand.getCardList());
        }

        // if there is lead suit in hand, play the smallest one
        if(currentHand.getNumberOfCardsWithSuit(leadSuit) != 0){
            ArrayList<Card> leadInHand = currentHand.getCardsWithSuit(leadSuit);
            return findSmallest(leadInHand);
        }// if the winning card is not trump suit, cannot play trump suit
        else if(winning.getSuit() != trumpSuit){
            Card smallest = null;
            for(Card card: currentHand.getCardList()){
                if(card.getSuit() != trumpSuit && smallest == null){
                    smallest = card;
                }else if(card.getSuit() != trumpSuit && card.getRankId() > smallest.getRankId()){
                    smallest = card;
                }
            }
            if(smallest != null) {
                return smallest;
            }
        }else{
            return findSmallest(currentHand.getCardList());
        }
        return currentHand.get(0);
    }

    private Card findSmallest(ArrayList<Card> list){
        Card smallest = list.get(0);
        for(Card card: list){
            if(card.getRankId() > smallest.getRankId()){
                smallest = card;
            }
        }
        return smallest;
    }

}
