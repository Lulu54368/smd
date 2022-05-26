package strategy;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.Oh_Heaven;
import oh_heaven.game.Round;
import player.Player;
import utils.Suit;

import java.util.ArrayList;

public class SmartStrategy implements IStrategy{

    private Hand.SortType sortType;

    public SmartStrategy() {
    }

    @Override
    public Card getNext(Player player, Round round) {

        Suit leadSuit = round.getLead();
        Suit trumpSuit = round.getTrump();
        Hand currentHand = player.getHand();

        currentHand.reverseSort(Hand.SortType.RANKPRIORITY, false);
        // as first player, play the smallest rank card and is not trump suit
        if(leadSuit == null){
            for(Card card : currentHand.getCardList()){
                if (card.compareTo(trumpSuit) != 0){
                    return card;
                }
            }
        }

        currentHand.sort(Hand.SortType.RANKPRIORITY, false);
        // set default card to play
        Card defaultCard = null;
        for(Card card : currentHand.getCardList()){
            if(card.compareTo(leadSuit) == 0){
                defaultCard = card;
                break;
            }
        }

        if(defaultCard == null){
            defaultCard = currentHand.getFirst();
        }

        currentHand.reverseSort(Hand.SortType.RANKPRIORITY, false);

        Card winningCard = round.getWinningCard();
        // check if any trump suit played in this round
        // if winning card is trump suit, return value will be 0
        // if the winning card is trump suit card play trump suit
        if(winningCard.compareTo(trumpSuit) == 0){
            ArrayList<Card> trumpInHand = currentHand.getCardsWithSuit(trumpSuit);
            // if there is trump suit in hand, play the largest
            if(trumpInHand.isEmpty() == false && trumpInHand.get(0).compareTo(winningCard) > 0){
                return trumpInHand.get(0);
            }
        }

        // find the largest rank of lead suit
        for(Card card: currentHand.getCardList()){
            if(card.compareTo(leadSuit) == 0 && card.compareTo(winningCard.getRank()) > 0){
                return card;
            }
        }

        // if winning card is trump suit, play the card with rank just greater than winning card
        // if winning card is not trump suit, play the card with rank just greater than lead suit
        // or there is no card with lead suit in hand, play the smallest trump suit

        return defaultCard;
    }
}
