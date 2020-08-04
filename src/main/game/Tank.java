package main.game;

import main.GameObject.GameObject;
import main.artillery.*;
import main.collision.CollisionHandler;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author anthony-pc
 */
public class Tank extends GameObject {

    private CollisionHandler collisionHandler;


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

    private boolean jump;
    private boolean lightingRoundsActivated;

    Tank(int x, int y, int vx, int vy, int angle, BufferedImage img, int identifier) {
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
        lifepoints = 3;
        lives = 3;
        this.jump = false;
        this.lightingRoundsActivated = false;
    }

    public Rectangle getHitbox() {
        return hitbox.getBounds();
    }


    public void setJump(Boolean boo) {
        this.jump = boo;
    }

    public boolean getJump() {
        return this.jump;
    }

    public void setLightingRoundsActivated(boolean boo) {
        this.lightingRoundsActivated = boo;
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
        // if key pressed, move in that direction
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
        // lifepoints subtracted
        if (lifepoints <= 0) {
            lifepoints = 3;
            lives--;
        }

        ammo.forEach(Ammo::update);
        handleBullets();

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
        hitbox.setLocation(x, y);
    }

    private void shoot() {
        Ammo bullet;
        // if pointing right
        if ((int) angle >= 0 && (int) angle < 45 || (int) angle > 315 && (int) angle <= 359 ||
                (int) angle < 0 && (int) angle >= -45 || (int) angle <= -315 && (int) angle >= -359) {

            if(lightingRoundsActivated){
                bullet = new LightningRound(x + 60, y, (int) angle, GameConstants.lightningRound);
            } else {
                bullet = new DefaultAmmo(x + 81, y + 5, (int) angle, GameConstants.missile);
            }
            //DefaultAmmo bullet = new DefaultAmmo(x + 81, y + 5, (int) angle, GameConstants.missile);
           //bullet = new LightningRound(x + this.getImg().getWidth() + 30, y + 5, (int) angle, GameConstants.lightningRound);
            ammo.add(bullet);
        }
        // if pointing down
        else if ((int) angle >= 45 && (int) angle <= 135 ||
                (int) angle <= -225 && (int) angle > -315) {
            if (lightingRoundsActivated){
                bullet = new LightningRound(x, y + 35, (int) angle, GameConstants.lightningRound);
            } else {
                bullet = new DefaultAmmo(x + 25, y + 36, (int) angle, GameConstants.missile);
            }

            ammo.add(bullet);
        }
        // if pointing left
        else if ((int) angle > 135 && (int) angle <= 225 ||
                (int) angle <= -135 && (int) angle > -225) {
            if (lightingRoundsActivated) {
                bullet = new LightningRound(x -60 , y, (int) angle, GameConstants.lightningRound);
            } else {
                bullet = new DefaultAmmo(x - 30, y + 15, (int) angle, GameConstants.missile);
            }

            ammo.add(bullet);
        }
        // if pointing up
        else if ((int) angle > 225 && (int) angle <= 315 ||
                (int) angle <= -45 && (int) angle > -135) {
            if (lightingRoundsActivated){
                bullet = new LightningRound(x , y - 30, (int) angle, GameConstants.lightningRound);
            } else {
                bullet = new DefaultAmmo(x + 25, y - 30, (int) angle, GameConstants.missile);
            }

            ammo.add(bullet);
        }

    }


    public void handleBullets() {
        collisionHandler.bulletCollision();
        for (Ammo bullet : ammo) {
            if (CollisionHandler.shot) {
                ammo.remove(bullet);
                CollisionHandler.setShot(false);
                break;
            } else if (CollisionHandler.wall) {
                ammo.remove(bullet);
                CollisionHandler.setWall(false);
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

    public BufferedImage getImg() {
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
