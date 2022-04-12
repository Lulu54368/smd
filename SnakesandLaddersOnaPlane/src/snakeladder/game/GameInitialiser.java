package snakeladder.game;

import snakeladder.utility.BackgroundDrawing;
import snakeladder.utility.PropertiesLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GameInitialiser {
    public static void main(String[] args) {
        Properties properties = null;
        if (args == null || args.length == 0) {
            properties = PropertiesLoader.loadPropertiesFile(null);
        } else {
            properties = PropertiesLoader.loadPropertiesFile(args[0]);
        }

        List<Connection> connections = new ArrayList<Connection>();
        connections.addAll(PropertiesLoader.loadSnakes(properties));
        //https://lucid.app/lucidchart/9be95504-3d34-4b04-a9a8-e03ab26099e3/edit?invitationId=inv_738eb634-27b8-4734-b2d7-7e90322878ce  connections.addAll(PropertiesLoader.loadLadders(properties));

        String backgroundImagePath = BackgroundDrawing.SPRITES_PATH + "gamepane_blank.png";
        String outputImagePath = BackgroundDrawing.SPRITES_PATH + "gamepane_snakeladder.png";
        BackgroundDrawing.addImageToBackground(backgroundImagePath, connections,
                GamePane.NUMBER_HORIZONTAL_CELLS, GamePane.NUMBER_VERTICAL_CELLS, outputImagePath);
        System.out.println("start loading image");

    }
}
