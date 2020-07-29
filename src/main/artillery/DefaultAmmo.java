package main.artillery;

import main.GameObject.GameObject;
import main.game.GameConstants;
import main.game.Tank;
import main.mapLayout.UnbreakableWall;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class DefaultAmmo extends GameObject {
    // instance variables
    int x_pos, y_pos, x_vel, y_vel, fire_angle;
    int R = 5;
    BufferedImage img;
    private static Rectangle hitbox;

    // constructor
    public DefaultAmmo(int x_position, int y_position, int angle, BufferedImage image){
        super(x_position, y_position, image);
        x_pos = x_position;
        y_pos = y_position;
        fire_angle = angle;
        img = image;
        this.hitbox = new Rectangle(x_pos, y_pos, this.img.getWidth(), this.img.getHeight());
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

    public void moveForwards() {
        x_vel = (int) Math.round(R * Math.cos(Math.toRadians(fire_angle)));
        y_vel = (int) Math.round(R * Math.sin(Math.toRadians(fire_angle)));
        x_pos += x_vel;
        y_pos += y_vel;
        this.hitbox.setLocation(x_pos, y_pos);
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
    public static Rectangle getHitbox() {
        return  hitbox.getBounds();
    }

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x_pos, y_pos);
        rotation.rotate(Math.toRadians(fire_angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        g2d.setColor(Color.BLUE);
        g2d.drawRect(x_pos, y_pos, this.img.getWidth(), this.img.getHeight());
    }

} // end class
