package snakeladder.game;

import snakeladder.game.GamePane;
import snakeladder.game.NavigationPane;
import snakeladder.game.Puppet;

public class ToggleStrategy implements Toggle{

    @Override
    public void operateToggle(GamePane gp, NavigationPane np) {
        //int DiceNum = np.getNumberODice();
         Puppet nextPlayer = gp.getNextPlayer();

        int DiceNum = 18;
        int numUp = 0, numDown = 0;
        for (int i=0; i<DiceNum; i++) {
            // if there is a up connection, upNum++; else downNum++;
            int currentCell = nextPlayer.getCellIndex()+i;
            if(gp.getConnectionAt(gp.cellToLocation(currentCell)) != null) {
                if (gp.cellToLocation(currentCell).y > currentCon.locStart.y)
            }
        }
    }
}
