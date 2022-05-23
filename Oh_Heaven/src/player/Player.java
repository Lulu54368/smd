package player;

import ch.aplu.jcardgame.Hand;
import utils.Suit;

import java.util.Random;

public class Player {
    private int score = 0;
    private int trick = 0;
    private int bid;
    private Hand hand;
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

    public int initBids(Suit trumps, int nbStartCards) {

       Random random = new Random();
       bid = nbStartCards / 4 + random.nextInt(2);
       return bid;



    }
}

