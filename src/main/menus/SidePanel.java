package main.menus;

import main.Launcher;
import main.game.GameConstants;
import main.game.GameSetup;
import main.game.Tank;

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
        drawLifePoints(t1, t1.lives, t1.red_lifepoints);
        drawLifePoints(t2, t2.lives, t2.blue_lifepoints);
    }


    public BufferedImage getSidePanel(){
        return this.sidePanel;
    }


    public void drawLifePoints(Tank t, int x, int y){
        int life_offset = 0;
        int height = 55;
        int health_offset = 55;
        int health_height = 25;
        for (int i = 0; i < x; i++){
            if (t.getImg() == GameConstants.blue_tank){
                height = 200;
            }
            g2.drawImage(GameConstants.life, life_offset, height, null);
            life_offset += 55;
        }

        for( int i = 0; i < y; i++){
            if (t.getImg() == GameConstants.blue_tank){
                health_height = 175;
            }
            g2.drawImage(GameConstants.health, health_offset, health_height, null);
            health_offset += 45;
        }
    }

//    public void update(){
//        //g2.drawImage(GameConstants.red_tank, 0, 0, null);
//        drawLifePoints(t1);
//        drawLifePoints(t2);
//    }

    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0,GameConstants.MINIMAP_SCREEN_WIDTH, 495);
        g2.drawImage(sidePanel,0,0,null);

    }
}
