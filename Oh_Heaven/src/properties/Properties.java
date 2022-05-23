package properties;

import ch.aplu.jcardgame.Deck;
import player.Human;
import player.NPC;
import player.Player;

import java.util.ArrayList;
import java.util.List;

public class Properties {
    private String version = "1.0";
    private int nbPlayers = 4;
    private  int nbStartCards = 13;
    private int nbRounds = 3;
    private  int madeBidBonus = 10;
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
        players.add(new Human("random", deck));
        for (int i= 0; i < nbPlayers-1; i++){
            players.add(new NPC("random", deck));
        }


        return players;
    }
}
