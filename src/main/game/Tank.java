package main.game;

import main.GameObject.GameObject;
import main.artillery.*;
import main.collision.CollisionHandler;

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

    private CollisionHandler collisionHandler;


    private int x;
    private int y;
    private int vx;
    private int vy;
    private static int x_pos;
    private static int y_pos;
    private float angle;
    public final ArrayList<DefaultAmmo> ammo;
    public Rectangle hitbox;

    private final int R = 2;
    private final float ROTATIONSPEED = 3.0f;

    public int lifepoints;
    public static int blue_lifepoints = 1;
    public static int red_lifepoints = 1;
    public int lives;
    public int identifier = 0;
    public static int blue_identifier ;
    public static int red_identifier;
    public static boolean endGame = false;

    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;

    private boolean jump;
    public static boolean wallHit = false;
    public static boolean blueWall = false;
    public static boolean redWall = false;
    private boolean collision;


    Tank(int x, int y, int vx, int vy, int angle, BufferedImage img, int identifier) {
        super(x, y, img);
        this.x = x;
        this.y = y;
        x_pos = this.x;
        y_pos = this.y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
        this.ammo = new ArrayList<>();
        hitbox = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());
        this.identifier = identifier;
        if (this.img == GameConstants.blue_tank) {
            blue_lifepoints = 3;
            blue_identifier = 2;
        } else if (this.img == GameConstants.red_tank) {
            red_lifepoints = 3;
            red_identifier = 1;
        }
        collisionHandler = new CollisionHandler(this);
        lifepoints = 3;
        lives = 3;
        this.jump = false;
    }

//    public Rectangle getHitbox() {
//        return  hitbox.getBounds();
//    }
    public int getIdentifier(){
        return this.identifier;
    }

//    public BufferedImage getImg(){
//        return  this.img;
//    }

    public void setJump(Boolean boo){
        this.jump = boo;
    }
    public boolean getJump(){
        return this.jump;
    }
    public void setY(int y){ this.y = y;}
    public int getY() { return y; }

    public int getX() {
        return x;
    }

    public int getVx() {
        return vx;
    }
    public void setVx(int x) {
       this.vx = x;
    }

    public int getVy() {
        return vy;
    }
    public void setVy(int y) {
        this.vy = y;
    }

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

    public void toggleCollision() { this.collision = true; }

    public void update() {
        if (this.lives == 0) {
            System.out.println("Game over");
            endGame = true;
        }

        this.collisionHandler.gotShot();

        //this.collisionHandler.gotShot();

//        if (wallHit) {
//            this.collisionHandler.wallCollision();
//            wallHit = false;
//        }
//        if (CollisionHandler.wall) {
//            this.collisionHandler.wallCollision();
//            CollisionHandler.setWall(false);
//        }

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
        if (this.blue_lifepoints == 0 && identifier == 2){
            //lifepoints = 3;
            lives--;
            blue_lifepoints = 3;
            setX(400);
            setY(400);
        }
        if (this.red_lifepoints == 0 && identifier == 1){
            lives--;
            red_lifepoints = 3;
            setX(100);
            setY(100);
        }

        ammo.forEach(DefaultAmmo::update);
        //checkBulletCollision();

    }

    public void setX(int x) {
        this.x = x;
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

    public void moveBackwards() {
            vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
            vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
            x -= vx;
            y -= vy;
            checkBorder();
            hitbox.setLocation(x, y);
    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        hitbox.setLocation(x,y);
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
            if (bullet.checkBorder()){
                itr.remove();
            }
            if (bullet.collisionDetected(this)){
                System.out.println(lifepoints);
                System.out.println(identifier);
                //lifepoints--;
                itr.remove();
            }
            if (CollisionHandler.wall){
                itr.remove();
                CollisionHandler.setWall(false);
            }

        } // end while
    } // end checkCollision

//    public boolean checkBulletCollision(){
//        for (DefaultAmmo bullet : ammo) {
//            if (bullet.checkBorder()) {
//                removeBullet();
//                return true;
//            }
//            if (bullet.collisionDetected(this)) {
//                System.out.println(lifepoints);
//                System.out.println(identifier);
//                removeBullet();
//                //lifepoints--;
//                return true;
//            }
//        }
//
//
//        return false;
//    } // end checkCollision
//
//    public void removeBullet(){
//        Iterator itr = ammo.iterator();
//        while(itr.hasNext()){
//            DefaultAmmo bullet = (DefaultAmmo) itr.next();
//            itr.remove();
//        }
//    }
//    void fixCollision(Wall w){
//        if(!w.collision()){
//        } else {
//            System.out.println("Beep");
//        }
//    }

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


    public int getLifePoints(){
        return this.lifepoints;
    }

    public void setLifepoints(int x) {
        this.lifepoints = x;
    }

    public BufferedImage getImg(){
        return img;
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
