package Main.Menus;

import Main.GameResources.GameConstants;
import Main.GameObject.Tank.Tank;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SidePanel extends JPanel {


    private BufferedImage sidePanel;
    Graphics2D g2;
    Tank t1, t2;


    public SidePanel(Tank t1, Tank t2){
        this.t1 = t1;
        this.t2 = t2;
        sidePanel = new BufferedImage(GameConstants.MINIMAP_SCREEN_WIDTH,
                495,
                                      BufferedImage.TYPE_INT_RGB);
        g2 = sidePanel.createGraphics();
        g2.drawImage(GameConstants.red_tank, 0, 0, null);
        g2.drawImage(GameConstants.blue_tank, 0, 150, null);
        drawLifePoints(t1, t1.lives, t1.lifepoints, t1.getBulletLoaded());
        drawLifePoints(t2, t2.lives, t2.lifepoints, t2.getBulletLoaded());
    }


    public BufferedImage getSidePanel(){
        return this.sidePanel;
    }


    public void drawLifePoints(Tank t, int x, int y, boolean bulletLoaded){
        int life_offset = 0;
        int height = 55;
        int health_offset = 55;
        int health_height = 25;
        for (int i = 0; i < x; i++){
            // change height for blue tank when drawing lives in side panel
            if (t.getImg() == GameConstants.blue_tank){
                height = 200;
            }
            // draw lives
            g2.drawImage(GameConstants.life, life_offset, height, null);
            life_offset += 55;
        }

        for( int i = 0; i < y; i++){
            // change height for blue tank when drawing lifepoints in side panel
            if (t.getImg() == GameConstants.blue_tank){
                health_height = 175;
            }
            // draw lifepoints
            g2.drawImage(GameConstants.health, health_offset, health_height, null);
            health_offset += 45;
        }
        // if bullet is loaded
        if (bulletLoaded) {
            // draw bullet
            if(t.getImg() == GameConstants.blue_tank)
            g2.drawImage(GameConstants.missile, 260, 175, null);
            else {
                g2.drawImage(GameConstants.missile, 260, 55, null);
            }
        }
    }

    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0,GameConstants.MINIMAP_SCREEN_WIDTH, 495);
        g2.drawImage(sidePanel,0,0,null);

    }
}
