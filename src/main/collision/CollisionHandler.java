package main.collision;

import main.GameObject.GameObject;
import main.artillery.DefaultAmmo;
import main.game.GameConstants;
import main.game.GameSetup;
import main.game.Tank;
import main.mapLayout.*;

import java.awt.*;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

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
        //public void checkBulletCollision(){
//            Iterator itr = t.ammo.iterator();
//            while(itr.hasNext()){
//                DefaultAmmo bullet = (DefaultAmmo) itr.next();
//                if (bullet.checkBorder()){
//                    itr.remove();
//                }
//                if (bullet.collisionDetected(t)){
//                    System.out.println(t.lifepoints);
//                    System.out.println(t.identifier);
//                    //lifepoints--;
//                    itr.remove();
//                }
////            if (CollisionHandler.wall){
////                itr.remove();
////                CollisionHandler.setWall(false);
////                return true;
////            }
//
//            } // end while
//        //} // end checkCollision

    }

    public void wallCollision() {
        System.out.println("From Tank CollisionHandler: " + wall);
            t.moveBackwards();
            setWall(false);
    }

    public void WallClassCollision(Tank t) throws InterruptedException {
        if(!t.getJump()) {
            if (w.getHitbox().intersects(t.hitbox) && w.isSolid()) {
                System.out.println("This came from CollisionHandler");
                t.moveBackwards();
//            t.setX(t.getX());
//            t.setY(t.getY());
                setWall(true);
            }
        }if (w.getHitbox().intersects(t.hitbox) && w instanceof Potion && !((Potion) w).isPickedUp()){
            ((Potion) w).setPickedUp(true);
            t.lives++;
            w.setImg(GameConstants.background);
        } if (w.getHitbox().intersects(t.hitbox) && w instanceof Jump && !((Jump) w).isPickedUp()){
            ((Jump) w).setPickedUp(true);
            w.setImg(GameConstants.background);
            t.setJump(true);
            //w.setSolid(false);
        }
        if (DefaultAmmo.hitbox == null){

        } else if (w.getHitbox().intersects(DefaultAmmo.hitbox)){
            if (w instanceof BreakableWall){
                System.out.println("I'm a breakable wall;");
                //w.subLife();
                w.setSolid(false);
                w.setImg(GameConstants.background);
                //System.out.println(w.getLife());
            } else if (w instanceof UnbreakableWall) {
                System.out.println("I'm an unbreakable wall;");
            }
        }

    }

//    public void JumpOn() throws InterruptedException {
//        if (w.getHitbox().intersects(t.hitbox)){
//            w.setSolid(false);
//        }
//        Thread.sleep(5000);
//        w.setSolid(true);
//    }


    public static void setWall(boolean boo){
        wall = boo;
    }

    public Tank getTank(){
        return t;
    }

}
