package main.game;

import main.GameObject.GameObject;
import main.artillery.*;
import main.mapLayout.UnbreakableWall;
import main.mapLayout.Wall;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author anthony-pc
 */
public class Tank extends GameObject {


    private int x;
    private int y;
    private int vx;
    private int vy;
    private float angle;
    private ArrayList<DefaultAmmo> ammo;
    private static Rectangle hitbox;

    private final int R = 2;
    private final float ROTATIONSPEED = 3.0f;

    public static int lifepoints = 3;
    public int lives = 3;
    public static boolean endGame = false;

    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;


    Tank(int x, int y, int vx, int vy, int angle, BufferedImage img) {
        super(x, y, img);
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
        this.ammo = new ArrayList<>();
        this.hitbox = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());

    }

    public static Rectangle getHitbox() {
        return  hitbox.getBounds();
    }

    void setX(int x){ this.x = x; }

    void setY(int y) { this. y = y;}

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void toggleShootPressed() { this.ShootPressed = true; }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void unToggleShootPressed() { this.ShootPressed = false; }

    public void update() {
        if (this.lives == 0) {
            System.out.println("Game over");
            endGame = true;
        }
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }
        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if (this.ShootPressed && GameSetup.tick % 30 == 0) {
            this.shoot();
        }
        if (this.lifepoints == 0 && this.img == GameConstants.blue_tank){
            this.lives--;
            this.lifepoints = 3;
            this.setX(400);
            this.setY(400);
        }
        if (this.lifepoints == 0 && this.img == GameConstants.red_tank){
            this.lives--;
            this.lifepoints = 3;
            this.setX(100);
            this.setY(100);
        }

        this.ammo.forEach(DefaultAmmo::update);
        checkBulletCollision();

    }

    private void rotateLeft() {
        if (this.angle <= -360) {
            this.angle = 0;
        }
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        if (this.angle >= 360) {
            this.angle = 0;
        }
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
        this.hitbox.setLocation(x,y);
    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        this.hitbox.setLocation(x,y);
    }

    private void shoot() {
        if((int) angle >= 0 && (int) angle < 45 || (int) angle > 315 && (int) angle <= 359 ||
                (int) angle < 0 && (int) angle >= -45 || (int) angle <= -315 && (int) angle >= -359){
            DefaultAmmo bullet = new DefaultAmmo(x + 50, y + 15, (int) angle, GameConstants.blue_missile);
            ammo.add(bullet);
        } else if((int) angle >= 45 && (int) angle <= 135 ||
                (int) angle <= -225 && (int) angle > -315) {
            DefaultAmmo bullet = new DefaultAmmo(x + 15, y + 50, (int) angle, GameConstants.blue_missile);
            ammo.add(bullet);
        }  else if((int) angle > 135 && (int) angle <= 225 ||
                (int) angle <= -135 && (int) angle > -225) {
            DefaultAmmo bullet = new DefaultAmmo(x - 20, y + 15, (int) angle, GameConstants.blue_missile);
            ammo.add(bullet);
        }  else if((int) angle > 225 && (int) angle <= 315 ||
                (int) angle <= -45 && (int) angle > -135) {
            DefaultAmmo bullet = new DefaultAmmo(x + 15, y - 20, (int) angle, GameConstants.blue_missile);
            ammo.add(bullet);
        }

    }


    public void checkBulletCollision(){
        Iterator itr = ammo.iterator();
        while(itr.hasNext()){
            DefaultAmmo bullet = (DefaultAmmo) itr.next();
            if (bullet.checkBorder()) itr.remove();
            if (DefaultAmmo.getHitbox().intersects(Tank.getHitbox())){
                System.out.println("Hit me!");
                lifepoints -= 1;
                itr.remove();
            }

        } // end while
    } // end checkCollision

    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.GAME_SCREEN_WIDTH - 88) {
            x = GameConstants.GAME_SCREEN_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.GAME_SCREEN_HEIGHT - 80) {
            y = GameConstants.GAME_SCREEN_HEIGHT - 80;
        }
    }

    public boolean collisionDetected(Rectangle rec){
        return this.getHitbox().intersects(rec.getBounds());
    }
    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }


    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        this.ammo.forEach(DefaultAmmo -> DefaultAmmo.drawImage(g));
        g2d.setColor(Color.RED);
        g2d.drawRect(x, y, this.img.getWidth(), this.img.getHeight());
    }



}
