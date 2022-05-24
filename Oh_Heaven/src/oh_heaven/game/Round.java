package oh_heaven.game;

import ch.aplu.jcardgame.*;
import properties.Properties;
import utils.Rank;
import utils.Suit;
import utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class Round {

    private Suit trump;


    //card played in this round
    private HashMap<Integer, HashSet<Card>> tricks;
    private Utils utils;
    private int winner;
    private Card winningCard;
    private Suit lead;

    public Round() {
        trump = utils.randomEnum(Suit.class);
    }



    public void setTricks(HashMap<Integer, HashSet<Card>> tricks) {
        this.tricks = tricks;
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
