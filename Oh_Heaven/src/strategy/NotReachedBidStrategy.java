package strategy;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.Round;
import player.Player;
import utils.Suit;

public class NotReachedBidStrategy {

    public Card selectCard(Player player, Round round) {
        Suit leadSuit = round.getLead();
        Suit trumpSuit = round.getTrump();
        Hand currentHand = player.getHand();

        //as first player, play the largest rank card and is not trump suit
        if (leadSuit == null) {
            Card largestCard = null;
            for (Card card : currentHand.getCardList()) {
                if (card.getSuit() != trumpSuit && largestCard == null){
                    largestCard = card;
                }else if (card.getSuit() != trumpSuit && card.getRankId() < largestCard.getRankId()) {
                    largestCard = card;
                }
            }
            return largestCard;
        }

        // set default card to play - same as lead suit and the rand id is the smallest one
        Card smallestLeadSuit = null;
        Card smallestOtherCard = null;
        Card smallestTrumpSuit = null;
        Card largestLeadSuit = null;
        Card largestTrumpSuit = null;

        for (Card card : currentHand.getCardList()) {
            if (card.getSuit() == leadSuit) {
                if (smallestLeadSuit == null) {
                    smallestLeadSuit = card;
                } else if (card.getRankId() > smallestLeadSuit.getRankId()) {
                    smallestLeadSuit = card;
                }

                if (largestLeadSuit == null) {
                    largestLeadSuit = card;
                } else if (card.getRankId() < largestLeadSuit.getRankId()) {
                    largestLeadSuit = card;
                }
            }
            if (card.getSuit() == trumpSuit) {
                if (largestTrumpSuit == null) {
                    largestTrumpSuit = card;
                } else if (card.getRankId() < largestTrumpSuit.getRankId()) {
                    largestTrumpSuit = card;
                }

                if (smallestTrumpSuit == null) {
                    smallestTrumpSuit = card;
                } else if (card.getRankId() > smallestTrumpSuit.getRankId()) {
                    smallestTrumpSuit = card;
                }
            }
            if (card.getSuit() != leadSuit && card.getSuit() != trumpSuit) {
                if (smallestOtherCard == null) {
                    smallestOtherCard = card;
                } else if (card.getRankId() > smallestOtherCard.getRankId()) {
                    smallestOtherCard = card;
                }
            }
        }


        Card winningCard = round.getWinningCard();

        // if there is lead suit in hand
        if (currentHand.getNumberOfCardsWithSuit(leadSuit) != 0 && leadSuit != trumpSuit) {
            // play the smallest if winning card is trump suit
            // or if the largest lead suit in hand is smaller than the winning card
            if (winningCard.getSuit() == trumpSuit ||
                    (winningCard.getSuit() == leadSuit && largestLeadSuit.getRankId() > winningCard.getRankId())) {
                if(smallestLeadSuit != null) {
                    return smallestLeadSuit;
                }
            } else if (largestLeadSuit.getRankId() < winningCard.getRankId() && largestLeadSuit != null) {
                // play the largest if the largest lead suit in hand is larger than winning card
                return largestLeadSuit;
            }
        } else { // if there is no lead suit in hand
            // if there is trump suit in hand
            if (currentHand.getNumberOfCardsWithSuit(trumpSuit) != 0 && leadSuit == trumpSuit) {
                if (largestTrumpSuit.getRankId() < winningCard.getRankId() && largestLeadSuit!= null) {
                    // play the largest trump suit if it is greater than winning card
                    return largestTrumpSuit;
                }else{
                    if(smallestTrumpSuit != null) {
                        return smallestTrumpSuit;
                    }
                }
            }
            if(smallestOtherCard != null) {
                return smallestOtherCard;
            }
        }
        return currentHand.get(0);
    }
}
