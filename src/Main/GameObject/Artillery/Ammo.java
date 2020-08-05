package Main.GameObject.Artillery;

import Main.GameObject.GameObject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Ammo extends GameObject {

    // instance variables
    int x_pos, y_pos, x_vel, y_vel, fire_angle;
    int R = 5;
    BufferedImage img;
    public static Rectangle hitbox;

    // constructor
    public Ammo(int x_position, int y_position, int angle, BufferedImage image){
        super(x_position, y_position, image);
        x_pos = x_position;
        y_pos = y_position;
        fire_angle = angle;
        img = image;
        hitbox = new Rectangle(x_pos, y_pos, this.img.getWidth(), this.img.getHeight());
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

    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x_pos, y_pos);
        rotation.rotate(Math.toRadians(fire_angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        g2d.setColor(Color.BLUE);
        g2d.drawRect(x_pos, y_pos, this.img.getWidth(), this.img.getHeight());
    }
}
