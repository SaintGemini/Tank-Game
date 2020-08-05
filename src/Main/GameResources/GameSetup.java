/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.GameResources;


import Main.GameObject.GameObject;
import Main.GameObject.Tank.Tank;
import Main.GameObject.Tank.TankControl;
import Main.Launcher;
import Main.MapResources.*;
import Main.Menus.SidePanel;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;


/**
 *
 * @author anthony-pc
 */
public class GameSetup extends JPanel implements Runnable {

    private BufferedImage world;
    private SidePanel sidePanel;
    private Launcher lf;
    public static long tick = 0;
    ArrayList<GameObject> gameObjects;
    public static ArrayList<Tank> tanks;

    public GameSetup(Launcher lf){
        this.lf = lf;
    }

    @Override
    public void run(){
       try {
           this.resetGame();
           while (true) {
               if (Tank.endGame) {
                   this.lf.setFrame("end");
                   return;
               }
               for (GameObject gameObject : this.gameObjects) {
                   gameObject.update();
               }
               this.sidePanel = new SidePanel(tanks.get(0), tanks.get(1));
                this.repaint();   // redraw game
                tick++;
                Thread.sleep(1000 / 144); //sleep for a few milliseconds
            }
       } catch (InterruptedException ignored) {
           System.out.println(ignored);
       }
    }



    /**
     * Reset game to its initial state.
     */
    public void resetGame(){
        this.tick = 0;
        Tank.endGame = false;
        for(GameObject t : gameObjects){
            if (t instanceof Tank){
                ((Tank) t).respawn();
                ((Tank) t).lives = 3;
                ((Tank) t).lifepoints = 3;
            }
        }
    }




    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize(){
        // creates blank world image
        this.world = new BufferedImage(GameConstants.GAME_SCREEN_WIDTH,
                                       GameConstants.GAME_SCREEN_HEIGHT,
                                       BufferedImage.TYPE_INT_RGB);

        try {
            // initialize arraylists
            gameObjects = new ArrayList<>();
            tanks = new ArrayList<>();
            // gets world map
            InputStreamReader isr = new InputStreamReader(GameSetup.class.getResourceAsStream("/resources/WorldMap"));
            BufferedReader mapReader = new BufferedReader(isr);
            String row = mapReader.readLine();
            if (row == null) {
                throw new IOException("no data read");
            }
            // parse through world map file
            String[] mapinfo = row.split("\t");
            int numCols = Integer.parseInt(mapinfo[0]);
            int numRows = Integer.parseInt(mapinfo[1]);
            // nested for loops to draw all walls and power ups
            for (int curRow = 0; curRow < numRows; curRow++) {
                row = mapReader.readLine();
                mapinfo = row.split("\t");
                for (int curCol = 0; curCol < numCols; curCol++) {
                    // add walls to gameObjects
                    switch (mapinfo[curCol]) {
                        case "2":
                            this.gameObjects.add(new BreakableWall(curCol * 30, curRow * 30, GameConstants.breakable_wall));
                            break;
                        case "5":
                            this.gameObjects.add(new Lightning(curCol * 30, curRow * 30, GameConstants.lightning_wall));
                            break;
                        case "6":
                            this.gameObjects.add(new Potion(curCol * 30, curRow * 30, GameConstants.potion));
                            break;
                        case "7":
                            this.gameObjects.add(new Jump(curCol * 30, curRow * 30, GameConstants.frog));
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

        // create tanks
        Tank t1 = new Tank(80, 80, 0, 0, 0, GameConstants.red_tank, 1);
        Tank t2 = new Tank(1150, 550, 0, 0, 180, GameConstants.blue_tank, 2);
        // creates sidepanel
        this.sidePanel = new SidePanel(t1, t2);
        // adds control to tanks
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        // add tanks to gameObjects
        this.gameObjects.add(t1);
        this.gameObjects.add(t2);
        tanks.add(t1);
        tanks.add(t2);
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
        // draws main screen
        g2.drawImage(world,0,0,null);
        // draws mini map
        g2.drawImage(world, 1260, 0, GameConstants.MINIMAP_SCREEN_WIDTH, GameConstants.MINIMAP_SCREEN_HEIGHT, null);
        // draw side panel
        g2.drawImage(sidePanel.getSidePanel(), 1260, GameConstants.MINIMAP_SCREEN_HEIGHT, GameConstants.MINIMAP_SCREEN_WIDTH, 495, null);
    }

}
