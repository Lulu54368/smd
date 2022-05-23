package properties;

public class Properties {
    private String version = "1.0";
    private int nbPlayers = 4;
    private  int nbStartCards = 13;
    private int nbRounds = 3;
    private  int madeBidBonus = 10;
    private final int handWidth = 400;
    private final int trickWidth = 40;

    public Properties Properties() {
        return new Properties();
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
}
