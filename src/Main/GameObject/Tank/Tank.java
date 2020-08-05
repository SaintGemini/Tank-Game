package Main.GameObject.Tank;

import Main.GameObject.GameObject;
import Main.GameObject.Artillery.*;
import Main.GameObject.CollisionHandler.CollisionHandler;
import Main.GameResources.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author anthony-pc
 */
public class Tank extends GameObject {

    private CollisionHandler collisionHandler;

    //instance variables
    private int x;
    private int y;
    private int vx;
    private int vy;
    private float angle;
    private ArrayList<Ammo> ammo;
    public Rectangle hitbox;

    private final int R = 2;
    private final float ROTATIONSPEED = 3.0f;

    public int lifepoints;
    public int lives;
    public int identifier = 0;
    public static boolean endGame = false;

    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;

    private boolean collision;
    private boolean bulletLoaded;
    private boolean jump;
    private boolean lightingRoundsActivated;

    public Tank(int x, int y, int vx, int vy, int angle, BufferedImage img, int identifier) {
        super(x, y, img);
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
        hitbox = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());
        ammo = new ArrayList<>();
        this.identifier = identifier;
        collisionHandler = new CollisionHandler(this);
        this.lifepoints = 3;
        this.lives = 3;
        this.jump = false;
        this.lightingRoundsActivated = false;
        this.bulletLoaded = true;
        this.collision = false;
    }

    // ------------------getters and setters----------------------
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) { this.y = y; }
    public void setAngle(int angle) { this.angle = angle; }
    public BufferedImage getImg() {
        return img;
    }

    public boolean getJump() {
        return this.jump;
    }
    public void setJump(Boolean boo) {
        this.jump = boo;
    }
    public void setLightingRoundsActivated(boolean boo) {
        this.lightingRoundsActivated = boo;
    }

    public boolean getBulletLoaded(){
        return this.bulletLoaded;
    }
    public void setBulletLoaded(boolean boo){
        this.bulletLoaded = boo;
    }

    public void setCollision(boolean boo){
        this.collision = boo;
    }
    // ----------------end getters and setters---------------------

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

    void toggleShootPressed() {
        this.ShootPressed = true;
    }

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

    void unToggleShootPressed() {
        this.ShootPressed = false;
    }

    public void update() {
        // if no more lives, end game
        if (this.lives == 0) {
            System.out.println("Game over");
            endGame = true;
        }

        collisionHandler.update();

        // if not collision
        if (!collision) {
            if (this.UpPressed) {
                this.moveForwards();
            }
            if (this.DownPressed) {
                this.moveBackwards();
            }
        }
        // if collision
        else {
            if (this.UpPressed) {
                this.moveBackwards();
                this.moveBackwards();
            }
            if (this.DownPressed) {
                this.moveForwards();
                this.moveForwards();
            }
            hitbox.setLocation(x-1, y-1);
            this.collision = false;
        }
        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        // if shoot pressed and bullet available
        if (this.ShootPressed && bulletLoaded) {
            // shoot
            this.shoot();
            // wait 1 second before loading another bullet
            setBulletLoaded(false);
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            setBulletLoaded(true);
                        }
                    },
                    1000
            );
        }
        // lifepoints reset and subtract a life
        if (lifepoints <= 0) {
            lifepoints = 3;
            lives--;
            respawn();
        }
        // update bullets
        ammo.forEach(Ammo::update);
        // handle bullets if collision detected
        handleBullets();
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

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        hitbox.setLocation(x, y);
    }

    public void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
        hitbox.setLocation(x, y);
    }

    // when shoot is pressed, add bullet to ammo
    private void shoot() {
        Ammo bullet;
        // if pointing east
        if ((int) angle >= 0 && (int) angle < 30 || (int) angle >= 330 && (int) angle <= 360 ||
                (int) angle < 0 && (int) angle >= -30 || (int) angle <= -330 && (int) angle >= -360) {
            if(lightingRoundsActivated){
                bullet = new LightningRound(x + this.img.getWidth() + 15, y + 3, (int) angle, GameConstants.lightningRound);
            } else {
                bullet = new DefaultAmmo(x + 56, y + 10, (int) angle, GameConstants.missile);
            }
            ammo.add(bullet);
        }
        // if pointing southeast
        else if ((int) angle >= 30 && (int) angle < 60 || (int) angle > 330 && (int) angle <= 300){
            if(lightingRoundsActivated){
                bullet = new LightningRound(x + this.img.getWidth() + 15, y + this.img.getHeight(), (int) angle, GameConstants.lightningRound);
            } else {
                bullet = new DefaultAmmo(x + 56, y + this.img.getHeight(), (int) angle, GameConstants.missile);
            }
            ammo.add(bullet);
        }

        // if pointing south
        else if ((int) angle >= 60 && (int) angle < 120 ||
                (int) angle <= -240 && (int) angle > -300) {
            if (lightingRoundsActivated){
                bullet = new LightningRound(x, y + 35, (int) angle, GameConstants.lightningRound);
            } else {
                bullet = new DefaultAmmo(x + 8, y + 36, (int) angle, GameConstants.missile);
            }

            ammo.add(bullet);
        }
        // if pointing southwest
        else if((int) angle >= 120 && (int) angle < 150 ||
                (int) angle >= -240 && (int) angle < -210){
            // TODO
            if (lightingRoundsActivated){
                bullet = new LightningRound(x - 60, y + 30, (int) angle, GameConstants.lightningRound);
            } else {
                bullet = new DefaultAmmo(x - 25, y + 45, (int) angle, GameConstants.missile);
            }

            ammo.add(bullet);
        }
        // if pointing west
        else if ((int) angle >= 150 && (int) angle < 210 ||
                (int) angle >= -210 && (int) angle < -150) {
            if (lightingRoundsActivated) {
                bullet = new LightningRound(x -60 , y, (int) angle, GameConstants.lightningRound);
            } else {
                bullet = new DefaultAmmo(x - 30, y + 10, (int) angle, GameConstants.missile);
            }
            ammo.add(bullet);
        }
        // if pointing northwest
        else if ((int) angle >= 210 && (int) angle < 240 ||
                (int) angle >= -150 && (int) angle < -120){
            // TODO
            if (lightingRoundsActivated) {
                bullet = new LightningRound(x - 60 , y-30, (int) angle, GameConstants.lightningRound);
            } else {
                bullet = new DefaultAmmo(x - 30, y - 15, (int) angle, GameConstants.missile);
            }
            ammo.add(bullet);
        }
        // if pointing north
        else if ((int) angle >= 240 && (int) angle < 300 ||
                (int) angle >= -120 && (int) angle < -60) {
            if (lightingRoundsActivated){
                bullet = new LightningRound(x , y - 30, (int) angle, GameConstants.lightningRound);
            } else {
                bullet = new DefaultAmmo(x + 10, y - 30, (int) angle, GameConstants.missile);
            }
            ammo.add(bullet);
        }
        // if pointing northeast
        else if ((int) angle >= 300 && (int) angle < 330 ||
                (int) angle >= -60 && (int) angle < -30){
            // TODO
            if(lightingRoundsActivated){
                bullet = new LightningRound(x + this.img.getWidth() + 15, y - 40, (int) angle, GameConstants.lightningRound);
            } else {
                bullet = new DefaultAmmo(x + 52, y - 10, (int) angle, GameConstants.missile);
            }
            ammo.add(bullet);
        }

    }


    public void handleBullets() {
        // checks bullet -> tank collision
        collisionHandler.checkBulletCollision();
        // for every bullet fired
        for (Ammo bullet : ammo) {
            // if collided with tank
            if (CollisionHandler.wasShot) {
                // remove bullet and set "shot state" to false
                ammo.remove(bullet);
                CollisionHandler.setWasShot(false);
                break;
            }
            // if collided with wall
            else if (CollisionHandler.wallState) {
                // remove bullet and set "shot wall" to false
                ammo.remove(bullet);
                CollisionHandler.setWallState(false);
                break;
            }
        }// end while
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



    // respawns tanks to their respective spawn zone
    public void respawn(){
        // for red tank
        if (this.identifier == 1){
            this.setX(60);
            this.setY(60);
            hitbox.setLocation(x, y);
        }
        // for blue tank
        if (this.identifier == 2){
            this.setX(1150);
            this.setY(550);
            this.setAngle(180);
            hitbox.setLocation(x, y);
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }


    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        AffineTransform rot = AffineTransform.getRotateInstance(Math.toRadians(angle), getX() + (this.img.getWidth() / 2.0), getY() + (this.img.getHeight() / 2.0));
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        g2d.setPaint(Color.BLUE);
        //g2d.draw(rot.createTransformedShape(this.hitbox));
        g2d.draw(hitbox);
        this.ammo.forEach(DefaultAmmo -> DefaultAmmo.drawImage(g));
        g2d.setColor(Color.RED);
    }
}
