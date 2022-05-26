package properties;

import ch.aplu.jcardgame.Deck;
import player.Human;
import player.NPC;
import player.Player;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Properties {
    private final String version = "1.0";
    private final int nbPlayers = 4;
    private final int nbStartCards = 13;
    private final int nbRounds = 3;
    private final int madeBidBonus = 10;
    private final int handWidth = 400;
    private final int trickWidth = 40;



    public String getVersion() {
        return version;
    }

    public int getNbPlayers() {
        return nbPlayers;
    }

    public int getNbStartCards() {
        return nbStartCards;
    }

    public int getNbRounds() {
        return nbRounds;
    }

    public int getMadeBidBonus() {
        return madeBidBonus;
    }

    public int getHandWidth() {
        return handWidth;
    }

    public int getTrickWidth() {
        return trickWidth;
    }

    public List<Player> configPlayer(Deck deck){
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Human(deck));
        for (int i= 0; i < nbPlayers-1; i++){
            players.add(new NPC("random", deck));
        }


        return players;
    }
}
