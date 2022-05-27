package strategy;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.Round;
import player.Player;
import utils.Rank;
import utils.Suit;

import java.util.ArrayList;

public class SmartStrategy implements IStrategy{

    public SmartStrategy() {
    }

    @Override
    public Card getNext(Player player, Round round) {

        Suit leadSuit = round.getLead();
        Suit trumpSuit = round.getTrump();
        Hand currentHand = player.getHand();

        //as first player, play the largest rank card and is not trump suit
        System.out.println("Lead suit:" + leadSuit);
        System.out.println(("-------------------"));
        if(leadSuit == null){
            Card largestCard = currentHand.getFirst();
            for(Card card : currentHand.getCardList()){
                if (card.getSuit() != trumpSuit && card.getRankId() < largestCard.getRankId()){
                    largestCard = card;
                    System.out.println("current largest:" + largestCard);
                    System.out.println("current largest:" + largestCard);
                    System.out.println("current largest:" + largestCard);
                    System.out.println("current largest:" + largestCard);
                    System.out.println("current largest:" + largestCard);
                    System.out.println("----------------");
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

        for(Card card : currentHand.getCardList()){
            if(card.getSuit() == leadSuit){
                if(smallestLeadSuit == null){
                    smallestLeadSuit = card;
                }else if(card.getRankId() > smallestLeadSuit.getRankId()){
                    smallestLeadSuit = card;
                }

                if(largestLeadSuit == null){
                    largestLeadSuit = card;
                }else if(card.getRankId() < largestLeadSuit.getRankId()){
                    largestLeadSuit = card;
                }
            }
            if(card.getSuit() == trumpSuit){
                if(largestTrumpSuit == null){
                    largestTrumpSuit = card;
                }else if(card.getRankId() < largestTrumpSuit.getRankId()){
                    largestTrumpSuit = card;
                }

                if(smallestTrumpSuit == null){
                    smallestTrumpSuit = card;
                }else if(card.getRankId() > smallestTrumpSuit.getRankId()){
                    smallestTrumpSuit = card;
                }
            }
            if(card.getSuit() != leadSuit && card.getSuit() != trumpSuit){
                if(smallestOtherCard == null){
                    smallestOtherCard = card;
                }else if(card.getRankId() > smallestOtherCard.getRankId()){
                    smallestOtherCard = card;
                }
            }
        }


        Card winningCard = round.getWinningCard();
//        if(winningCard.getSuit() == trumpSuit){
//            if(largestTrumpSuit.getRankId() > winningCard.getRankId()) {
//                return largestTrumpSuit;
//            }
//        }

        // if there is lead suit in hand
        if(currentHand.getNumberOfCardsWithSuit(leadSuit) != 0 && leadSuit != trumpSuit){
            // play the smallest if winning card is trump suit
            // or if the largest lead suit in hand is smaller than the winning card
            if(winningCard.getSuit() == trumpSuit ||
                    (winningCard.getSuit() == leadSuit && largestLeadSuit.getRankId() > winningCard.getRankId())){
                return smallestLeadSuit;
            }else if(largestLeadSuit.getRankId() < winningCard.getRankId()){
                // play the largest if the largest lead suit in hand is larger than winning card
                return largestLeadSuit;
            }
        }else{ // if there is no lead suit in hand
            // if there is trump suit in hand
            if(currentHand.getNumberOfCardsWithSuit(trumpSuit) != 0 && leadSuit == trumpSuit){
                if(largestTrumpSuit.getRankId() < winningCard.getRankId()){
                    // play the largest trump suit if it is greater than winning card
                    return largestTrumpSuit;
                }else{
                    return smallestTrumpSuit;
                }
            }
            return smallestOtherCard;
        }



        // if there is no lead suit in hand, find the smallest one
//        if(defaultCard == null){
//            for(Card card : currentHand.getCardList()){
//                if(card.getSuit() == leadSuit){
//                    if(defaultCard == null){
//                        defaultCard = card;
//                    }else if(card.getRankId() < defaultCard.getRankId()){
//                        defaultCard = card;
//                    }
//                }
//            }
//            //defaultCard = currentHand.getFirst();
//        }

//        currentHand.sort(Hand.SortType.RANKPRIORITY, false);
//
//        Card winningCard = round.getWinningCard();
//
//        // check if any trump suit played in this round
//        // if winning card is trump suit, return value will be 0
//        // if the winning card is trump suit card play trump suit
//        if(winningCard.getSuit() == trumpSuit){
//            ArrayList<Card> trumpInHand = currentHand.getCardsWithSuit(trumpSuit);
//            // if there is trump suit in hand, play the largest
//            if(trumpInHand.isEmpty() == false && trumpInHand.get(trumpInHand.size()-1).compareTo(winningCard) > 0){
//                return trumpInHand.get(trumpInHand.size()-1);
//            }
//        }
//
//        // find the largest rank of lead suit
//        for(Card card: currentHand.getCardList()){
//            if(card.getSuit() == leadSuit && card.compareTo(winningCard) > 0){
//                return card;
//            }
//        }

//        return defaultCard;
        return smallestLeadSuit;
    }
}
