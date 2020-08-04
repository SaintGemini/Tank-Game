package main.collision;

import main.artillery.DefaultAmmo;
import main.game.GameConstants;
import main.game.Tank;
import main.mapLayout.*;

import java.awt.*;
import java.util.Iterator;

public class CollisionHandler {
    private Tank t;
    private Wall w;

    public static boolean wall = false;
    public static boolean shot;

    public CollisionHandler(Tank t) {
        this.t = t;
        shot = false;
    }

    public CollisionHandler(Wall w) {
        this.w = w;

    }

    public void bulletCollision() {
            if (DefaultAmmo.hitbox == null) {
            } else {
                    if (DefaultAmmo.hitbox.intersects(t.getHitbox())) {
                        System.out.println(t.identifier);
                        setShot(true);
                        DefaultAmmo.hitbox = new Rectangle();
                        t.lifepoints--;

                        //t.checkBulletCollision();

                    }
            }
    }


    // Handles wall collisions
    //TODO add third powerup
    public void WallClassCollision(Tank t) {


        // if tank does not have jump power up
        if (!t.getJump()) {
            // if tank runs into wall
            if (w.getHitbox().intersects(t.hitbox) && w.isSolid()) {
                System.out.println("This came from CollisionHandler");
                t.moveBackwards();
            }
        }
        // if tank picks up potion
        if (w.getHitbox().intersects(t.hitbox) && w instanceof Potion && !((Potion) w).isPickedUp()) {
            ((Potion) w).setPickedUp(true);
            t.lives++;
            w.setImg(GameConstants.background);
        }
        // if tank picks up jump
        if (w.getHitbox().intersects(t.hitbox) && w instanceof Jump && !((Jump) w).isPickedUp()) {
            ((Jump) w).setPickedUp(true);
            w.setImg(GameConstants.background);
            t.setJump(true);
            // wait 5 seconds then turn jump power up off
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            t.setJump(false);
                        }
                    },
                    5000
            );
        }
        // if there are no bullets, do nothing
        if (DefaultAmmo.hitbox == null) {

        } else {
            // if bullet collides with wall
            if (w.getHitbox().intersects(DefaultAmmo.hitbox)) {
                // if breakable wall
                if (w instanceof BreakableWall) {
                    // wall no longer solid
                    w.setSolid(false);
                    w.setImg(GameConstants.background);
                    // if unbreakable wall, do nothing
                } else if (w instanceof UnbreakableWall) {
                    //System.out.println("I'm an unbreakable wall;");
                }
                setWall(true);
            }
        }// end bullet -> wall


    } // end wall collision


    public static void setWall(boolean boo) {
        wall = boo;
    }

    public static void setShot(boolean boo){
        shot = boo;
    }

}
