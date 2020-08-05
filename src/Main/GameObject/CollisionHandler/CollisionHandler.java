package Main.GameObject.CollisionHandler;

import Main.GameObject.Artillery.Ammo;
import Main.GameResources.GameConstants;
import Main.GameObject.Tank.Tank;
import Main.MapResources.*;

import java.awt.*;

public class CollisionHandler {
    private Tank t;
    private Wall w;

    public static boolean wallState = false;
    public static boolean wasShot;
    public static boolean lightning_round = false;

    // constructor for tank collision handler
    public CollisionHandler(Tank t) {
        this.t = t;
        wasShot = false;
    }
    // constructor for wall collision handler
    public CollisionHandler(Wall w) {
        this.w = w;

    }

    //check bullet -> tank collision
    public void checkBulletCollision() {
            // if no bullets
            if (Ammo.hitbox == null) {
                // do nothing (handles null pointer exception)
            } else {
                    // if bullet collides with tank
                    if (Ammo.hitbox.intersects(t.getHitbox())) {
                        // was shot is true
                        setWasShot(true);
                        // resets hitbox to ensure collision only happens once
                        // instead of "collision detected" every frame
                        Ammo.hitbox = new Rectangle();
                        // if DefaultAmmo, life points is subtracted by one
                        if (!lightning_round) {
                            t.lifepoints--;
                        } else {
                            // if lightning round, subtract by 2
                            t.lifepoints -= 2;
                        }
                    }
            }
    }


    // Handles wall collisions
    public void WallClassCollision(Tank t) {
        // if tank does not have jump power-up
        // (Jump power-up lets you travel "over" walls)
        if (!t.getJump()) {
            // if tank runs into wall
            if (w.getHitbox().intersects(t.hitbox) && w.isSolid()) {
                // set wall collision state for tank to true
                // this is handled in Tank update function
                t.wallCollision = true;
            }
        }
        // if tank picks up potion
        if (w.getHitbox().intersects(t.hitbox) && w instanceof Potion && !((Potion) w).isPickedUp()) {
            // potion is picked up
            ((Potion) w).setPickedUp(true);
            // plus one life
            t.lives++;
            // change image
            w.setImg(GameConstants.background);
        }
        // if tank picks up jump
        if (w.getHitbox().intersects(t.hitbox) && w instanceof Jump && !((Jump) w).isPickedUp()) {
            // jump power-up is picked up
            ((Jump) w).setPickedUp(true);
            // change image
            w.setImg(GameConstants.background);
            // set jump state to true
            t.setJump(true);
            // wait 5 seconds then turn jump state to false
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
        // if tank picks up lightning rounds
        if (w.getHitbox().intersects(t.hitbox) && w instanceof Lightning && !((Lightning) w).isPickedUp()) {
            // lighting rounds is picked up
            ((Lightning) w).setPickedUp(true);
            // activate lightning rounds in tank class
            t.setLightingRoundsActivated(true);
            lightning_round = true;
            // change image
            w.setImg(GameConstants.background);
            // wait 10 seconds then deactivate lightning rounds
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            t.setLightingRoundsActivated(false);
                            lightning_round = false;
                        }
                    },
                    10000
            );
        }
        // bullet -> wall collisions below
        // if there are no bullets
        if (Ammo.hitbox == null) {
            // do nothing (handles null pointer exception)
        } else {
            // if bullet collides with wall
            if (w.getHitbox().intersects(Ammo.hitbox)) {
                // if breakable wall
                if (w instanceof BreakableWall) {
                    // wall no longer solid
                    w.setSolid(false);
                    w.setImg(GameConstants.background);
                }
                setWallState(true);
            }
        }// end bullet -> wall


    } // end wall collision


    public static void setWallState(boolean boo) {
        wallState = boo;
    }

    public static void setWasShot(boolean boo){
        wasShot = boo;
    }

}
