package main.collision;

import java.awt.*;

public class HitBox  extends Rectangle {
    private int x_pos, y_pos, width, height;
    private float angle;

    public HitBox(int x, int y, int width, int height, float angle){
        this.x_pos = x;
        this.y_pos = y;
        this.width = width;
        this.height = height;
        this.angle = angle;
    }
}
