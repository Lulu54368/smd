package strategy;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.Round;
import player.Player;
import utils.Suit;
import utils.Utils;

import java.util.ArrayList;

public class LegalStrategy implements IStrategy{

    @Override
    public Card getNext(Player player, Round round) {

        Hand currentHand = player.getHand();
        Suit leadSuit = round.getLead();

        // if is first player
        if(leadSuit == null){
            return Utils.randomCard(currentHand);
        }

        //To check if player have the same suit as lead suit
        ArrayList<Card> sameAsLead = currentHand.getCardsWithSuit(leadSuit);

        if(sameAsLead.isEmpty() == true){
            // if there is no same suit as the lead suit, play randomly
            return Utils.randomCard(currentHand);
        }else{
            return Utils.randomCard(sameAsLead);
        }

    }
}
