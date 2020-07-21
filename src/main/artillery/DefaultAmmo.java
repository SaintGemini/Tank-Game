package main.artillery;

import main.game.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class DefaultAmmo {
    // instance variables
    int x_pos, y_pos, x_vel, y_vel, fire_angle;
    int R = 5;
    BufferedImage img;
    Rectangle hitbox;

    // constructor
    public DefaultAmmo(int x_position, int y_position, int angle, BufferedImage image){
        x_pos = x_position;
        y_pos = y_position;
        fire_angle = angle;
        img = image;
        hitbox = new Rectangle(x_pos, y_pos, 20, 20);
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
    }

    public void update(){
        moveForwards();
    }

    private void checkBorder() {
        if (x_pos < 30) {
            x_pos = 30;
        }
        if (x_pos >= GameConstants.GAME_SCREEN_WIDTH - 88) {
            x_pos = GameConstants.GAME_SCREEN_WIDTH - 88;
        }
        if (y_pos < 40) {
            y_pos = 40;
        }
        if (y_pos >= GameConstants.GAME_SCREEN_HEIGHT - 80) {
            y_pos = GameConstants.GAME_SCREEN_HEIGHT - 80;
        }
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
