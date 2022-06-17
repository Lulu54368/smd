package oh_heaven.game;

import ch.aplu.jcardgame.*;
import utils.Suit;
import utils.Utils;

import java.util.HashMap;
import java.util.HashSet;

public class Round {

    private final Suit trump;


    //card played in this round
    private final HashMap<Integer, HashSet<Card>> tricks;
    private Utils utils;
    private int winner;
    private Card winningCard;
    private Suit lead;

    public Round(Suit trumps) {
        this.trump = trumps;
        this.tricks = new HashMap<>();
    }


    public void setTricks(int player, Card selected) {
        if (tricks.containsKey(player)) {
            tricks.get(player).add(selected);
        } else {
            tricks.put(player, new HashSet<>());
        }
    }



    public void setWinner(int winner) {
        this.winner = winner;
    }

    public void setWinningCard(Card winningCard) {
        this.winningCard = winningCard;
    }

    public void setLead(Suit lead) {
        this.lead = lead;
    }

    public Suit getTrump() {
        return trump;
    }

    public HashMap<Integer, HashSet<Card>> getTricks() {
        return tricks;
    }



    public int getWinner() {
        return winner;
    }

    public Card getWinningCard() {
        return winningCard;
    }

    public Suit getLead() {
        return lead;
    }
}
