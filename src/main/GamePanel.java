package main;

import maps.MapHandler;
import objects.Bomb;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {


    public final int tileSize = 48; //48x48 tile
    public final int maxScreenCol = 19;
    public final int maxScreenRow = 13;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    public TileManager tileManager;
    public Thread thread;
    public KeyHandler keyHandler = new KeyHandler(this);
    public CollisionHandler collisionHandler = new CollisionHandler(this);
    public MapHandler mapHandler = new MapHandler(this);
    public menu menu = new menu(this);

    public int hoodedSkeletonsNum = 1;
    public int simpleSkeletonsNum = 2;
    public int ghostsNum = 3;
    public int enemiesNum = hoodedSkeletonsNum + simpleSkeletonsNum + ghostsNum;
    public Enemy[] enemies = new Enemy[50];
    public Player player = new Player(this, keyHandler, mapHandler);


    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int settingState = 3;
    public final int winState = 4;
    public final int loseState = 5;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler); //this panel can recognize key input
        this.setFocusable(true);
        mapHandler.readMap();
        mapHandler.setMap();
        tileManager = new TileManager(this);
        gameState = titleState;
        setEnemies();
    }

    public void setEnemies() {
        for (int i = 0; i < 50; i++) {
            if (i < ghostsNum) {
                enemies[i] = new Ghost(this);
                enemies[i].setBounds();
            } else if (i < ghostsNum + simpleSkeletonsNum) {
                enemies[i] = new SimpleSkeleton(this);
                enemies[i].setBounds();
            } else if (i < enemiesNum) {
                enemies[i] = new HoodedSkeleton(this);
                enemies[i].setBounds();
            }
        }
    }

    public void start() {
        setEnemies();
        thread = new Thread(this);
        thread.start(); //automatically call run method
    }

    //create game loop
    @Override
    public void run() {
        // 1.UPDATE: update information such as character positions
        // 2.DRAW: draw the screen with the updated information

        //drawing the screen every 0.016 seconds
        double interval = 1000000000 / 60;//0.0166666666 seconds
        double nextDrawTime = System.nanoTime() + interval;

        while (thread != null) {
            update();
            repaint();

            double remainingTime = nextDrawTime - System.nanoTime();
            remainingTime = remainingTime / 1000000;
            //if update and repaint took more than this draw interval then no time is left!
            if (remainingTime < 0) {
                remainingTime = 0;
            }

            try {
                Thread.sleep((long) remainingTime);
                nextDrawTime += interval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void update() {
        //PLAYING
        if (gameState == playState) {
            player.update();
            for (int i = 0; i < enemies.length; i++) {
                if (enemies[i] != null)
                    enemies[i].update();
            }
        }
        if (gameState == pauseState) {

        }

        //LOSING
        if (player.getHp() <= 0) {
            gameState = loseState;
        }

        //WINNING
        boolean win = true;
        for (int i = 0; i < enemies.length; i++) {
            if (enemies[i] != null) {
                win = false;
            }
        }
        if (win) {
            gameState = winState;
        }

    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (gameState == playState || gameState == pauseState) {
            //WALLS
            tileManager.draw(g2);

            //PLAYER
            player.draw(g2);
            //ENEMIES
            for (int i = 0; i < enemies.length; i++) {
                if (enemies[i] != null)
                    enemies[i].draw(g2);
            }
            g2.setFont(new Font("Arial", Font.PLAIN, 25));
            g2.setColor(Color.white);

            String txt = String.valueOf(player.powerBooster);
            int x = 145;
            int y = screenHeight;
            g2.drawString(txt, x, y);

            txt = "bomb power:";
            x = 0;
            g2.drawString(txt, x, y);

            txt = "bomb radius:";
            x = 170;
            g2.drawString(txt, x, y);

            txt = String.valueOf(player.radiusBooster);
            x=320;
            g2.drawString(txt, x, y);

            g2.dispose();
        } else {
            menu.draw(g2);

        }

    }
}
