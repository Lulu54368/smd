package player;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Deck;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.Round;
import utils.Suit;

import java.util.Random;

public abstract class Player {
    protected int score = 0;
    protected int trick = 0;
    protected int bid;
    protected Hand hand ;

    protected Card selected;
    public Player(Deck deck) {

        this.hand = new Hand(deck);
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
        if (trick == bid) score += madeBidBonus;
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

    public Card getSelected() {
        return selected;
    }

    public void setSelected(Card selected) {
        this.selected = selected;
    }

    public abstract Card getSelectedCard(Round round);
}

