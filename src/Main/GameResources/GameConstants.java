package Main.GameResources;

import Main.Launcher;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

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
    public static BufferedImage startImage = null;
    public static BufferedImage blue_tank = null;
    public static BufferedImage red_tank = null;

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

    //public static FileInputStream fs = null;

    static {
        try {
            startImage = read(Objects.requireNonNull(Launcher.class.getClassLoader().getResource("title.png")));
            blue_tank = read(Objects.requireNonNull(Launcher.class.getClassLoader().getResource("blue_tank.png")));
            red_tank = read(Objects.requireNonNull(Launcher.class.getClassLoader().getResource("red_tank.png")));
            missile = read(Objects.requireNonNull(Launcher.class.getClassLoader().getResource("spr_missile_.png")));
            unbreakable_wall = read(Objects.requireNonNull(Launcher.class.getClassLoader().getResource("unbreakable_wall.jpg")));
            breakable_wall = read(Objects.requireNonNull(Launcher.class.getClassLoader().getResource("breakable_wall.jpg")));
            wallpaper = read(Objects.requireNonNull(Launcher.class.getClassLoader().getResource("wallpaper.jpg")));
            life = read(Objects.requireNonNull(Launcher.class.getClassLoader().getResource("heart2.png")));
            health = read(Objects.requireNonNull(Launcher.class.getClassLoader().getResource("blood_red_bar2.png")));
            background = read(Objects.requireNonNull(Launcher.class.getClassLoader().getResource("background.jpg")));
            potion = read(Objects.requireNonNull(Launcher.class.getClassLoader().getResource("red-potion.png")));
            frog = read(Objects.requireNonNull(Launcher.class.getClassLoader().getResource("cutepixelfrog_16x16.gif")));
            lightning_wall = read(Objects.requireNonNull(Launcher.class.getClassLoader().getResource("lightning_wall.png")));
            lightningRound = read(Objects.requireNonNull(Launcher.class.getClassLoader().getResource("lightning.png")));

            //fs = new FileInputStream("src/resources/acid_tanks.wav");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
