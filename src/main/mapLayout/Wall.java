package main.mapLayout;

import main.GameObject.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.CyclicBarrier;

public abstract class Wall extends GameObject {
    int x, y;
    BufferedImage img;
    private Rectangle hitbox;

    Wall(int x,int y, BufferedImage image){
        super(x, y, image);
        this.x = x;
        this.y = y;
        this.img = image;
        this.hitbox = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());

    }
    public Rectangle getHitbox(){
        return hitbox.getBounds();
    }


    public void drawImage(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.GREEN);
        //g2.drawRect(x,y,this.img.getWidth(),this.img.getHeight());
        //g2.draw(this.hitbox);
        g2.drawImage(this.img, x, y, null);
    }

}
