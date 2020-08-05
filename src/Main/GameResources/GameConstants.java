package Main.GameResources;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class GameConstants {
    public static final int SCREEN_WIDTH = 1575;
    public static final int SCREEN_HEIGHT = 660;
    public static final int MINIMAP_SCREEN_WIDTH = 315; // 1/4 of game screen
    public static final int MINIMAP_SCREEN_HEIGHT = 165; // 1/4 of game screen
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
    public static BufferedImage red_tank2 = null;

    public static BufferedImage missile = null;
    public static BufferedImage lightningRound = null;

    public static BufferedImage unbreakable_wall = null;
    public static BufferedImage breakable_wall = null;
    public static BufferedImage wallpaper = null;
    public static BufferedImage life = null;
    public static BufferedImage health = null;
    public static BufferedImage background = null;
    public static BufferedImage potion = null;
    public static BufferedImage frog = null;
    public static BufferedImage lightning_wall = null;

    public static FileInputStream fs = null;

    static {
        try {
            startImage = ImageIO.read(new File("src/resources/title.png"));
            tankImage = ImageIO.read(new File("src/resources/tank1.png"));
            blue_tank = ImageIO.read((new File("src/resources/blue_tank.png")));
            red_tank = ImageIO.read((new File("src/resources/red_tank.png")));
            red_tank2 = ImageIO.read((new File("src/resources/red_tank2.png")));
            missile = ImageIO.read((new File("src/resources/spr_missile_.png")));
            unbreakable_wall = ImageIO.read(new File( "src/resources/unbreakable_wall.jpg"));
            breakable_wall = ImageIO.read(new File("src/resources/breakable_wall.jpg"));
            wallpaper = ImageIO.read(new File("src/resources/wallpaper.jpg"));
            life = ImageIO.read(new File("src/resources/heart2.png"));
            health = ImageIO.read(new File("src/resources/blood_red_bar2.png"));
            background = ImageIO.read(new File("src/resources/background.jpg"));
            potion = ImageIO.read(new File("src/resources/red-potion.png"));
            frog = ImageIO.read(new File("src/resources/cutepixelfrog_16x16.gif"));
            lightning_wall = ImageIO.read(new File("src/resources/lightning_wall.png"));
            lightningRound = ImageIO.read(new File("src/resources/lightning.png"));

            fs = new FileInputStream("./src/resources/acid_tanks.wav");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
