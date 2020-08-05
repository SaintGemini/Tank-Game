package Main.GameObject.Artillery;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DefaultAmmo extends Ammo {
    // instance variables
    int x_pos, y_pos, x_vel, y_vel, fire_angle;
    int R = 5;
    BufferedImage img;
    public static Rectangle hitbox;

    // constructor
    public DefaultAmmo(int x_position, int y_position, int angle, BufferedImage image){
        super(x_position, y_position, angle, image);
        x_pos = x_position;
        y_pos = y_position;
        fire_angle = angle;
        img = image;
        hitbox = new Rectangle(x_pos, y_pos, this.img.getWidth(), this.img.getHeight());
    }
} // end class
