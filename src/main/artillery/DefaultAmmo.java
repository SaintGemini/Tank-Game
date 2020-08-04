package main.artillery;

import main.GameObject.GameObject;
import main.collision.CollisionHandler;
import main.game.GameConstants;
import main.game.GameSetup;
import main.game.Tank;
import main.mapLayout.UnbreakableWall;
import main.mapLayout.Wall;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;



public class DefaultAmmo extends GameObject {
    // instance variables
    int x_pos, y_pos, x_vel, y_vel, fire_angle;
    int R = 5;
    BufferedImage img;
    public static Rectangle hitbox;
    private static boolean hit;

    public static Tank t1 = (Tank) GameSetup.tanks.get(0);
    public static Tank t2 = (Tank) GameSetup.tanks.get(1);


    // constructor
    public DefaultAmmo(int x_position, int y_position, int angle, BufferedImage image){
        super(x_position, y_position, image);
        x_pos = x_position;
        y_pos = y_position;
        fire_angle = angle;
        img = image;
        hitbox = new Rectangle(x_pos, y_pos, this.img.getWidth(), this.img.getHeight());
        hit = false;

    }

    void setX_pos(int x) {
        x_pos = x;
    }

    void setY_pos(int y) {
        y_pos = y;
    }

    void setX_vel(int x) {
        x_vel = x;
    }

    void setY_vel(int y) {
        y_vel = y;
    }

    void setImg(BufferedImage image) {
        img = image;
    }

    public void setHit(boolean boo){
        hit = boo;
    }

    public boolean getHit(){
        return hit;
    }

    public void moveForwards() {
        x_vel = (int) Math.round(R * Math.cos(Math.toRadians(fire_angle)));
        y_vel = (int) Math.round(R * Math.sin(Math.toRadians(fire_angle)));
        x_pos += x_vel;
        y_pos += y_vel;
        hitbox.setLocation(x_pos, y_pos);
    }

    public void update(){
        moveForwards();
    }

    public boolean checkBorder() {
        if (x_pos < 30) {
            return true;
        }
        if (x_pos >= GameConstants.GAME_SCREEN_WIDTH - 50) {
            return true;
        }
        if (y_pos < 30) {
            return true;
        }
        if (y_pos >= GameConstants.GAME_SCREEN_HEIGHT - 50) {
            return true;
        }
        return false;
    }

//    public boolean collisionDetected(Tank t){
//        if (this.getHitbox().intersects(t.hitbox)){
//            if (t.getImg() == GameConstants.blue_tank){
//                System.out.println("Red Tank was shot");
//                //Tank.red_lifepoints--;
//                //System.out.println("Blue tank");
//            } else {
//                System.out.println("Blue Tank was shot");
//                //Tank.blue_lifepoints--;
//            }
//            //t.lifepoints--;
//            //System.out.println("doesnt burn");
//            System.out.println("Tank: " + t.identifier + " was shot.");
//            return true;
//        }
//        return false;
//    }

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x_pos, y_pos);
        rotation.rotate(Math.toRadians(fire_angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        g2d.setColor(Color.BLUE);
        g2d.drawRect(x_pos, y_pos, this.img.getWidth(), this.img.getHeight());
    }

} // end class
