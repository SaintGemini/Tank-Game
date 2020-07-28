package main.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    int x, y;
    BufferedImage img;


    public GameObject(int x_pos, int y_pos, BufferedImage image){
        x = x_pos;
        y = y_pos;
        this.img = image;
    }

    public abstract void drawImage(Graphics g);
    public abstract void update();
}
