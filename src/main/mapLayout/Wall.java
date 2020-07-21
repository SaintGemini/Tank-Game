package main.mapLayout;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Wall {
    int x, y;
    BufferedImage img;
    Wall(int x,int y, BufferedImage image){
        this.x = x;
        this.y = y;
        this.img = image;
    }
    public void drawImage(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.img, x, y, null);
    }
}
