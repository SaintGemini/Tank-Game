package main.mapLayout;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Potion extends Wall {
    private Rectangle hitbox;
    private boolean solid;
    private boolean pickedUp;
    public Potion(int x, int y, BufferedImage image) {
        super(x, y, image);
        hitbox = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());
        this.solid = false;
        this.pickedUp = false;
    }

    public boolean isSolid(){
        return this.solid;
    }

    public void setPickedUp(boolean boo){
        this.pickedUp = boo;
    }

    public boolean isPickedUp(){
        return this.pickedUp;
    }
}
