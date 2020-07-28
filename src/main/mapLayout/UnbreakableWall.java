package main.mapLayout;

import main.game.Tank;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UnbreakableWall extends Wall{

    private Rectangle hitbox;
    public UnbreakableWall(int x, int y, BufferedImage image) {
        super(x, y, image);
        hitbox = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());
    }
    public Rectangle getHitBox(){
        return hitbox.getBounds();
    }
//    public boolean wallCollision(){
//        if (getHitBox().intersects(Tank.getHitbox())){
//            System.out.println("Hit a wall");
//            return true;
//        }
//        return false;
//    }
    @Override
    public void update() {

    }
}
