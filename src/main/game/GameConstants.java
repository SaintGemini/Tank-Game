package main.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameConstants {
    public static final int SCREEN_WIDTH = 1500;
    public static final int SCREEN_HEIGHT = 1000;
    public static final int GAME_SCREEN_WIDTH = 1260; // 42
    public static final int GAME_SCREEN_HEIGHT = 660; // 22

    public static final int START_MENU_SCREEN_WIDTH = 500;
    public static final int START_MENU_SCREEN_HEIGHT = 550;

    public static final int END_MENU_SCREEN_WIDTH = 500;
    public static final int END_MENU_SCREEN_HEIGHT = 500;
    public static Image startImage = null;
    public static Image tankImage = null;
    public static BufferedImage blue_tank = null;
    public static BufferedImage red_tank = null;
    public static BufferedImage blue_missile = null;
    public static BufferedImage red_missile = null;
    public static BufferedImage unbreakable_wall = null;
    public static BufferedImage breakable_wall = null;

    static {
        try {
            startImage = ImageIO.read(new File("src/resources/title.png"));
            tankImage = ImageIO.read(new File("src/resources/tank1.png"));
            blue_tank = ImageIO.read((new File("src/resources/blue_tank.png")));
            red_tank = ImageIO.read((new File("src/resources/red_tank.png")));
            blue_missile = ImageIO.read((new File("src/resources/blue_missile.jpg")));
            red_missile = ImageIO.read((new File("src/resources/red_missile.jpg")));
            unbreakable_wall = ImageIO.read(new File( "src/resources/unbreakable_wall.jpg"));
            breakable_wall = ImageIO.read(new File("src/resources/breakable_wall.jpg"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
