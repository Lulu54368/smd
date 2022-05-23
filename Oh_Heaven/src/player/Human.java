package player;

import ch.aplu.jcardgame.*;

public class Human extends Player{
    public Human(String strategy, Deck deck) {
        super(strategy, deck);
        setCardListener();
    }
    // Set up human player for interaction
    public void setCardListener(){
        CardListener cardListener = new CardAdapter()  // Human Player plays card
        {
            public void leftDoubleClicked(Card card) {
                selected = card;
                hand.setTouchEnabled(false);
            }
        };

        hand.addCardListener(cardListener);

    }

}
