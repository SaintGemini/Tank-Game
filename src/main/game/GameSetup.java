/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.game;


import main.GameObject.GameObject;
import main.Launcher;
import main.artillery.DefaultAmmo;
import main.mapLayout.BreakableWall;
import main.mapLayout.UnbreakableWall;
import main.mapLayout.Wall;
import main.menus.SidePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 *
 * @author anthony-pc
 */
public class GameSetup extends JPanel implements Runnable {

    private BufferedImage world;
    private BufferedImage miniworld;
    private SidePanel sidePanel;
    private Launcher lf;
    static long tick = 0;
    ArrayList<GameObject> gameObjects;
    public static ArrayList<Tank> tanks;

    public GameSetup(Launcher lf){
        this.lf = lf;
    }

    @Override
    public void run(){
       try {
           //this.resetGame();
           while (true) {
               if (Tank.endGame) {
                   this.lf.setFrame("end");
                   return;
               }
                this.gameObjects.forEach(GameObject::update);
                this.sidePanel = new SidePanel(tanks.get(0), tanks.get(1));
                this.repaint();   // redraw game
                tick++;
                Thread.sleep(1000 / 144); //sleep for a few milliseconds
                /*
                 * simulate an end game event
                 * we will do this with by ending the game when drawn 2000 frames have been drawn
                 */
//                if(this.tick > 2000){
//                    this.lf.setFrame("end");
//                    return;
//                }
            }
       } catch (InterruptedException ignored) {
           System.out.println(ignored);
       }
    }

    /**
     * Reset game to its initial state.
     */
//    public void resetGame(){
//        int count = 0;
//        this.tick = 0;
////        this.t1.setX(300);
////        this.t1.setY(300);
////        this.t2.setX(500);
////        this.t2.setY(500);
//        for(GameObject t : gameObjects){
//            if (t instanceof Tank){
//                if(count == 1){
//                    ((Tank) t).setX(500);
//                    ((Tank) t).setY(400);
//                }
//                count++;
//                ((Tank) t).setX(300);
//                ((Tank) t).setY(400);
//            }
//        }
//    }


    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize() throws IOException {
        this.world = new BufferedImage(GameConstants.GAME_SCREEN_WIDTH,
                                       GameConstants.GAME_SCREEN_HEIGHT,
                                       BufferedImage.TYPE_INT_RGB);

        //sidePanel.drawLifePoints();

        try {
            gameObjects = new ArrayList<>();
            tanks = new ArrayList<>();
            InputStreamReader isr = new InputStreamReader(GameSetup.class.getResourceAsStream("/resources/WorldMap"));
            BufferedReader mapReader = new BufferedReader(isr);

            String row = mapReader.readLine();

            if (row == null) {
                throw new IOException("no data read");
            }
            String[] mapinfo = row.split("\t");
            int numCols = Integer.parseInt(mapinfo[0]);
            int numRows = Integer.parseInt(mapinfo[1]);
            for (int curRow = 0; curRow < numRows; curRow++) {
                row = mapReader.readLine();
                mapinfo = row.split("\t");
                for (int curCol = 0; curCol < numCols; curCol++) {
                    switch (mapinfo[curCol]) {
                        case "2":
                            this.gameObjects.add(new BreakableWall(curCol * 30, curRow * 30, GameConstants.breakable_wall));
                            break;
                        case "3":
                        case "9":
                            this.gameObjects.add(new UnbreakableWall(curCol * 30, curRow * 30, GameConstants.unbreakable_wall));
                    }
                }
            }

        } catch (IOException e) {
            System.out.println(e);
        }

        Tank t1 = new Tank(300, 300, 0, 0, 0, GameConstants.red_tank, 1);
        Tank t2 = new Tank(500, 500, 0, 0, 180, GameConstants.blue_tank, 2);

        this.sidePanel = new SidePanel(t1, t2);

        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);

        this.gameObjects.add(t1);
        this.gameObjects.add(t2);
        System.out.println(gameObjects.size());
        this.tanks.add(t1);
        this.tanks.add(t2);
        this.setBackground(Color.BLACK);
        this.lf.getJf().addKeyListener(tc1);
        this.lf.getJf().addKeyListener(tc2);
    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        buffer.setColor(Color.BLACK);
        buffer.fillRect(0,0,GameConstants.SCREEN_WIDTH,GameConstants.SCREEN_HEIGHT);
        this.gameObjects.forEach(gameObject -> gameObject.drawImage(buffer));
        g2.drawImage(world,0,0,null);
        g2.drawImage(world, 1260, 0, GameConstants.MINIMAP_SCREEN_WIDTH, GameConstants.MINIMAP_SCREEN_HEIGHT, null);
        g2.drawImage(sidePanel.getSidePanel(), 1260, GameConstants.MINIMAP_SCREEN_HEIGHT, GameConstants.MINIMAP_SCREEN_WIDTH, 495, null);
    }

}
