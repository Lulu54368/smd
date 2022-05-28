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
import java.util.Properties;

public class Property {

    private final String version = "1.0";
    private final int nbPlayers = 4;
    private int nbStartCards;
    private int nbRounds;
    private final int madeBidBonus = 10;
    private final int handWidth = 400;
    private final int trickWidth = 40;
    private ArrayList<String> playerTypes;

    public void setPlayerTypes(ArrayList<String> playerTypes) {
        this.playerTypes = playerTypes;
    }

    public void setNbStartCards(int nbStartCards) {
        this.nbStartCards = nbStartCards;
    }

    public void setNbRounds(int nbRounds) {
        this.nbRounds = nbRounds;
    }

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
        List<Player> players = new ArrayList<>();
        for(String type: playerTypes){
            Player newPlayer;
            if(type.equals("human")){
                newPlayer = new Human(deck);
            }else{
                newPlayer = new NPC(type, deck);
            }
            newPlayer.setScore(0);
            players.add(newPlayer);
        }
        return players;
    }
}
