package Main.MapResources;

import Main.GameObject.GameObject;
import Main.GameObject.CollisionHandler.CollisionHandler;
import Main.GameResources.GameSetup;
import Main.GameObject.Tank.Tank;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Wall extends GameObject {
    private int x, y;
    BufferedImage img;
    public Rectangle hitbox;
    private CollisionHandler collisionHandler;
    private boolean solid;

    private static Tank t1;
    private static Tank t2;

    Wall(int x,int y, BufferedImage image){
        super(x, y, image);
        this.x = x;
        this.y = y;
        this.img = image;
        this.hitbox = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());
        collisionHandler = new CollisionHandler(this);
        this.solid = true;
    }


    public void update() {
        // if in solid state
        if (solid) {
            // if tanks have been created and initialized
            if (t1 != null && t2 != null) {
                // check and correct collisions
                collisionHandler.WallClassCollision(t1);
                collisionHandler.WallClassCollision(t2);
            } else {
                // get tanks
                t1 = (Tank) GameSetup.tanks.get(0);
                t2 = (Tank) GameSetup.tanks.get(1);
            }
        }
    }

    public boolean isSolid(){
        return this.solid;
    }

    public void setSolid(boolean boo){
        this.solid = boo;
    }

    public void setImg(BufferedImage image){
        this.img = image;
    }

    public void drawImage(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.drawImage(this.img, x, y, null);
    }

}
