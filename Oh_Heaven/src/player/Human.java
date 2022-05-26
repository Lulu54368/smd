package player;

import ch.aplu.jcardgame.*;
import oh_heaven.game.Oh_Heaven;
import oh_heaven.game.Round;

public class Human extends Player{

    private Card selected;

    public Human(Deck deck) {
        super(deck);
        setCardListener();
    }

    @Override
    public Card getSelectedCard(Round round) {
        return selected;
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
