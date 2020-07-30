package main.mapLayout;

import main.GameObject.GameObject;
import main.artillery.DefaultAmmo;
import main.game.Tank;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall extends Wall{
    private Rectangle hitbox;
    public BreakableWall(int x, int y, BufferedImage image) {

        super(x, y, image);
        hitbox = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());
    }

    @Override
    public void update() {
        if (getHitbox().intersects(Tank.getHitbox())){
            System.out.println("bruh");
        }
//        else if (DefaultAmmo.getHitbox().intersects(getHitbox())){
//            System.out.println("doesnt burn");
//        }
    }
//    public Rectangle getHitbox(){
//        return hitbox.getBounds();
//    }
    public void drawImage(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.GREEN);
        //g2.drawRect(x,y,this.img.getWidth(),this.img.getHeight());
        g2.draw(this.hitbox);
        g2.drawImage(this.img, x, y, null);
    }

}
