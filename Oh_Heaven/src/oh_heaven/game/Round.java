package oh_heaven.game;

import ch.aplu.jcardgame.*;
import properties.Properties;
import utils.Rank;
import utils.Suit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class Round {
    private Hand[] hands;
    Properties properties;
    private Suit trump;


    //card played in this round
    private HashMap<Integer, HashSet<Card>> tricks;
    private ArrayList<Integer> scores;
    private int winner;
    private Card winningCard;
    private Suit lead;

    public Hand[] getHands() {
        return hands;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setHands(Hand[] hands) {
        this.hands = hands;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public void setTrump(Suit trump) {
        this.trump = trump;
    }

    public void setTricks(HashMap<Integer, HashSet<Card>> tricks) {
        this.tricks = tricks;
    }

    public void setScores(ArrayList<Integer> scores) {
        this.scores = scores;
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

    public ArrayList<Integer> getScores() {
        return scores;
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
