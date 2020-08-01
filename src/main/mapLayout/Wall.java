package main.mapLayout;

import main.GameObject.GameObject;
import main.artillery.DefaultAmmo;
import main.collision.CollisionHandler;
import main.game.GameSetup;
import main.game.Tank;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.concurrent.CyclicBarrier;

public abstract class Wall extends GameObject {
    private int x, y;
    BufferedImage img;
    public Rectangle hitbox;
    private CollisionHandler collisionHandler;
    private int lifepoints;
    private boolean solid;

    public static Tank t1;
    public static Tank t2;

    Wall(int x,int y, BufferedImage image){
        super(x, y, image);
        this.x = x;
        this.y = y;
        this.img = image;
        this.hitbox = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());
        collisionHandler = new CollisionHandler(this);
        this.lifepoints = 1;
        this.solid = true;


    }


    public void update() throws InterruptedException {
        //System.out.println(solid);
        if (solid) {
            if (t1 != null && t2 != null) {
                collisionHandler.WallClassCollision(t1);
                collisionHandler.WallClassCollision(t2);
            } else {
                //System.out.println(CollisionHandler.wall);
                t1 = (Tank) GameSetup.tanks.get(0);
                t2 = (Tank) GameSetup.tanks.get(1);
            }
        }
//        if (DefaultAmmo.hitbox == null){
//
//        } else if (this.getHitbox().intersects(DefaultAmmo.hitbox)){
//            if (this instanceof BreakableWall){
//                System.out.println("I'm a breakable wall;");
//                lifepoints--;
//                System.out.println(lifepoints);
//            } else if (this instanceof UnbreakableWall) {
//                System.out.println("I'm an unbreakable wall;");
//            }
//            CollisionHandler.setWall(true);
//        }



    }

    public int getLife(){
        return this.lifepoints;
    }

    public void subLife(){
        this.lifepoints--;
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

//    public boolean collision(){
//        if (this.getHitbox().intersects(t.hitbox)){
//            t.wallHit = true;
//            System.out.println("bruh");
//            return true;
//        }
//        if (DefaultAmmo.hitbox == null){
//
//        } else if (this.getHitbox().intersects(DefaultAmmo.hitbox)){
//            System.out.println("doesnt burn");
//            return true;
//        }
//        return false;
//    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void drawImage(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        //g2.drawRect(x,y,this.img.getWidth(),this.img.getHeight());
        //g2.draw(this.hitbox);
        g2.drawImage(this.img, x, y, null);
    }

}
