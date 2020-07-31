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

    public static Tank t1;
    public static Tank t2;

    Wall(int x,int y, BufferedImage image){
        super(x, y, image);
        this.x = x;
        this.y = y;
        this.img = image;
        this.hitbox = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());
        collisionHandler = new CollisionHandler(this);



    }
//    public Rectangle getHitbox(){
//        return hitbox.getBounds();
//    }

    public void update() {
        //collision();
//        if (this.getHitbox().intersects(Tank.hitbox)){
////            if (Tank.dentifier == 2){
////                Tank.blueWall = true;
////            }
////            else if (Tank.red_identifier == 1) {
////                Tank.redWall = true;
////            }
//            //Tank.collisionHandler.wallCollision();
////            GameObject.COLLISION = true;
//            CollisionHandler.wall = true;
//            System.out.println("Wall class recognizes Collision");
//            //return true;
//        }



//        t2 = (Tank) GameSetup.gameObjects.get(189);
//        t1 = (Tank) GameSetup.gameObjects.get(188);
        if (t1 != null && t2 != null) {
            collisionHandler.WallClassCollision(t1);
            collisionHandler.WallClassCollision(t2);
        } else {
            //System.out.println(CollisionHandler.wall);
            t1 = (Tank) GameSetup.tanks.get(0);
            t2 = (Tank) GameSetup.tanks.get(1);
        }
        if (DefaultAmmo.hitbox == null){

        } else if (this.getHitbox().intersects(DefaultAmmo.hitbox)){
            if (this instanceof BreakableWall){
                System.out.println("I'm a breakable wall;");
            } else if (this instanceof UnbreakableWall) {
                System.out.println("I'm an unbreakable wall;");
            }
            CollisionHandler.setWall(true);
            //return true;
        }



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
