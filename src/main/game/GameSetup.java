/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.game;


import main.Launcher;
import main.artillery.DefaultAmmo;
import main.mapLayout.BreakableWall;
import main.mapLayout.UnbreakableWall;
import main.mapLayout.Wall;

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
    private BufferedImage unbreakable_wall;
    private BufferedImage breakable_wall;
    private Tank t1;
    private Tank t2;
    private DefaultAmmo ammo;
    private Launcher lf;
    static long tick = 0;
    ArrayList<Wall> walls;

    public GameSetup(Launcher lf){
        this.lf = lf;
    }

    @Override
    public void run(){
       try {
           this.resetGame();
           while (true) {
                this.t1.update(); // update tank
                this.t2.update();
                this.repaint();   // redraw game
                tick++;
//               if(t1.getHitbox().intersects(t2.getHitbox())){
//                   System.out.println("Collision detected.");
//               }
//               if(t1.collisionDetected()){
//                   System.out.println("Yes");
//               }

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
    public void resetGame(){
        this.tick = 0;
        this.t1.setX(300);
        this.t1.setY(300);
        this.t2.setX(500);
        this.t2.setY(500);
    }


    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize() throws IOException {
        this.world = new BufferedImage(GameConstants.GAME_SCREEN_WIDTH,
                                       GameConstants.GAME_SCREEN_HEIGHT,
                                       BufferedImage.TYPE_INT_RGB);
        BufferedImage t1img = GameConstants.red_tank;
        BufferedImage t2img = GameConstants.blue_tank;
        try {

            breakable_wall = GameConstants.breakable_wall;
            unbreakable_wall = GameConstants.unbreakable_wall;
            walls = new ArrayList<>();
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
                            this.walls.add(new BreakableWall(curCol * 30, curRow * 30, breakable_wall));
                            break;
                        case "3":
                        case "9":
                            this.walls.add(new UnbreakableWall(curCol * 30, curRow * 30, unbreakable_wall));
                    }
                }
            }

        } catch (IOException e) {
            System.out.println(e);
        }

        t1 = new Tank(300, 300, 0, 0, 0, t1img);
        t2 = new Tank(500, 500, 0, 0, 0, t2img);

        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);

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
        this.walls.forEach(wall -> wall.drawImage(buffer));
        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);
        g2.drawImage(world,0,0,null);
    }

}
