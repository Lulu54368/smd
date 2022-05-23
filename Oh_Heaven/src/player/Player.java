package player;

import ch.aplu.jcardgame.Hand;
import utils.Suit;

import java.util.Random;

public abstract class Player {
    private int score = 0;
    private int trick = 0;
    private int bid;
    private Hand hand;
    private String strategy;

    public Player(String strategy) {
        this.strategy = strategy;
    }

    public int getScore() {
        return score;
    }

    public int getTrick() {
        return trick;
    }

    public int getBid() {
        return bid;
    }

    public void setScore(int madeBidBonus) {
        this.score += trick;
        if (trick == bid) score+= madeBidBonus;
    }

    public void setTrick(int trick) {
        this.trick = trick;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }
}

