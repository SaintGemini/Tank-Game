package Main.MapResources;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall extends Wall{
    private Rectangle hitbox;
    public BreakableWall(int x, int y, BufferedImage image) {

        super(x, y, image);
        hitbox = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());
    }


}
