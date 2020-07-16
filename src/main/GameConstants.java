package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameConstants {
    public static final int GAME_SCREEN_WIDTH = 1024;
    public static final int GAME_SCREEN_HEIGHT = 768;

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

    static {
        try {
            startImage = ImageIO.read(new File("resources/title.png"));
            tankImage = ImageIO.read(new File("resources/tank1.png"));
            blue_tank = ImageIO.read((new File("resources/blue_tank.png")));
            red_tank = ImageIO.read((new File("resources/red_tank.png")));
            blue_missile = ImageIO.read((new File("resources/blue_missile.jpg")));
            red_missile = ImageIO.read((new File("resources/red_missile.jpg")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
