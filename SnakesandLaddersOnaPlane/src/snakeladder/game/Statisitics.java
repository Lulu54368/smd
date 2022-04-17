package snakeladder.game;

import java.util.HashMap;
import java.util.Map;

public class Statisitics {
    private String playerName;
    private Map<Integer, Integer> rolledMap;
    private Integer travelUp = 0;
    private Integer travelDown = 0;

    public Statisitics() {

        rolledMap = new HashMap<>();
        for(int i = 1; i <= 6; i++)
            rolledMap.put(i, 0);
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Map<Integer, Integer> getRolledMap() {
        return rolledMap;
    }

    public Integer getTravelUp() {
        return travelUp;
    }

    public Integer getTravelDown() {
        return travelDown;
    }

    public void setRolledMap(Map<Integer, Integer> rolledMap) {
        this.rolledMap = rolledMap;
    }

    public void setTravelUp(Integer travelUp) {
        this.travelUp = travelUp;
    }

    public void setTravelDown(Integer travelDown) {
        this.travelDown = travelDown;
    }

    @Override
    public String toString() {
        return "playerName='" + playerName + '\'' +
                ", rolledMap=" + rolledMap +
                ", travelUp=" + travelUp +
                ", travelDown=" + travelDown + '\n';
    }
}
