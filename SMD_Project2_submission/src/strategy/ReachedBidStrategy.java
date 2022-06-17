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
            return LessThanWinningCard(currentHand.getCardsWithSuit(leadSuit), winning.getRankId());
        }// if the winning card is not trump suit, cannot play trump suit and play the largest to waste it
        else if(winning.getSuit() != trumpSuit){
            Card largest = findLargestOther(currentHand.getCardList(), trumpSuit);
            if(largest != null) {
                return largest;
            }
        }else{
            // if the winning card is trump suit, and have trump suit in hand
            // if player have the card just less than winning card, play this card
            // otherwise play the largest other suit
            if(currentHand.getNumberOfCardsWithSuit(trumpSuit) != 0){
                Card smallestTrump = LessThanWinningCard(currentHand.getCardsWithSuit(trumpSuit), winning.getRankId());
                if(smallestTrump.getRankId() > winning.getRankId()){
                    return smallestTrump;
                }
                return findLargest(currentHand.getCardList());
            }
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

    private Card findLargest(ArrayList<Card> list){
        Card largest = list.get(0);
        for(Card card: list){
            if(card.getRankId() < largest.getRankId()){
                largest = card;
            }
        }
        return largest;
    }

    private Card findLargestOther(ArrayList<Card> list, Suit trumpSuit){
        Card largest = null;
        for(Card card: list){
            if(card.getSuit() != trumpSuit && largest == null){
                largest = card;
            }else if(card.getSuit() != trumpSuit && card.getRankId() < largest.getRankId()){
                largest = card;
            }
        }
        return largest;
    }

    private Card LessThanWinningCard(ArrayList<Card> list, int winningRank){
        Card select = list.get(0);
        for(Card card: list){
            if(card.getRankId() < select.getRankId() && card.getRankId() > winningRank){
                select = card;
            }
        }
        return select;
    }

}
