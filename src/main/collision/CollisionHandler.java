package main.collision;

import main.GameObject.GameObject;
import main.artillery.DefaultAmmo;
import main.game.GameSetup;
import main.game.Tank;
import main.mapLayout.Wall;

import java.awt.*;
import java.util.Iterator;

public class CollisionHandler{
    private Tank t;
    private Wall w;


    public static boolean wall = false;

    public CollisionHandler(Tank t){
        this.t = t;
    }
    public CollisionHandler(Wall w) {
        this.w = w;

    }

    public void gotShot() {
       t.checkBulletCollision();

    }

    public void wallCollision() {
        System.out.println("From Tank CollisionHandler: " + wall);
            t.moveBackwards();
            setWall(false);
    }

    public void WallClassCollision(Tank t){
        if (w.getHitbox().intersects(t.hitbox)){
            System.out.println("This came from CollisionHandler");
            t.moveBackwards();
//            t.setX(t.getX());
//            t.setY(t.getY());
            setWall(true);
        }
    }

    public static void setWall(boolean boo){
        wall = boo;
    }

    public Tank getTank(){
        return t;
    }

}
