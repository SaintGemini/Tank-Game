package main.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    int x, y;
    BufferedImage img;
    private static Rectangle hitbox;

    public GameObject(int x_pos, int y_pos, BufferedImage image){
        x = x_pos;
        y = y_pos;
        this.img = image;
        hitbox = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());
    }
    public static Rectangle getHitbox(){
        return hitbox.getBounds();
    }
    public abstract void drawImage(Graphics g);
    public abstract void update();
}
