package main;

import javax.swing.*;
import java.awt.*;

public class menu {
    Graphics2D g2;
    GamePanel gamePanel;
    public int input = 0;
    public int settingInput = 0;
    private Image gameImage=new ImageIcon("C:\\Users\\LENOVO\\IdeaProjects\\bomber\\assets\\bomber-friends.jpg").getImage();

    public menu(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(new Font("Arial", Font.PLAIN, 60));
        g2.setColor(Color.white);

        if (gamePanel.gameState == gamePanel.titleState) {
            drawMainMenu();
        }
        if (gamePanel.gameState == gamePanel.playState) {

        }
        if (gamePanel.gameState == gamePanel.pauseState) {
            drawPauseScreen();
        }
        if (gamePanel.gameState == gamePanel.settingState) {
            drawSettingScreen();
        }
        if (gamePanel.gameState == gamePanel.winState) {
            drawWinScreen();
        }
        if (gamePanel.gameState == gamePanel.loseState) {
            drawLoseScreen();
        }
    }

    public void drawWinScreen() {
        g2.setFont(new Font("Arial", Font.PLAIN, 70));
        String txt = "YOU WIN";
        int x = getCenterX(txt);
        int y = gamePanel.screenHeight / 2;
        g2.drawString(txt, x, y);
    }

    public void drawLoseScreen() {
        g2.setFont(new Font("Arial", Font.PLAIN, 70));
        String txt = "YOU LOSE";
        int x = getCenterX(txt);
        int y = gamePanel.screenHeight / 2;
        g2.drawString(txt, x, y);
    }

    public void drawPauseScreen() {
        String pauseTxt = "PAUSE";
        int x = getCenterX(pauseTxt);
        int y = gamePanel.screenHeight / 2;
        g2.drawString(pauseTxt, x, y);
    }

    public void drawMainMenu() {
        g2.setFont(new Font("Arial", Font.PLAIN, 70));

        g2.drawImage(gameImage,50,50,gamePanel.getWidth()-100,300,null);
        int x;
        int y;
        //Menu
        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        String text = "1.new game";
        x = getCenterX(text);
        y = gamePanel.tileSize * 9;
        g2.drawString(text, x, y);
        if (input == 0) {
            g2.drawString("->", x - 30, y);
        }

        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        text = "2.setting";
        x = getCenterX(text);
        y = gamePanel.tileSize * 10;
        g2.drawString(text, x, y);
        if (input == 1) {
            g2.drawString("->", x - 30, y);
        }

        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        text = "3.exit";
        x = getCenterX(text);
        y = gamePanel.tileSize * 11;
        g2.drawString(text, x, y);
        if (input == 2) {
            g2.drawString("->", x - 30, y);
        }

    }


    public void drawSettingScreen() {
        g2.setColor(Color.white);
        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        settingOptions();

    }

    public void settingOptions() {
        int x, y;

        //title
        String txt = "SETTING";
        x = getCenterX(txt);
        y = gamePanel.tileSize;
        g2.drawString(txt, x, y);

        g2.setFont(new Font("Arial", Font.PLAIN, 22));
        //simple skeleton number
        txt = "simpleSkeleton number";
        x = 2 * gamePanel.tileSize;
        y = gamePanel.tileSize * 2;
        g2.drawString(txt, x, y);
        if (settingInput == 0) {
            g2.drawString("->", x - 30, y);
        }
        g2.drawString(String.valueOf(gamePanel.simpleSkeletonsNum), x + 12 * gamePanel.tileSize, y);

        //hooded skeleton number
        txt = "hoodedSkeleton number";
        y = gamePanel.tileSize * 4;
        g2.drawString(txt, x, y);
        if (settingInput == 1) {
            g2.drawString("->", x - 30, y);
        }
        g2.drawString(String.valueOf(gamePanel.hoodedSkeletonsNum), x + 12 * gamePanel.tileSize, y);

        //ghost number
        txt = "ghost number";
        y = gamePanel.tileSize * 6;
        g2.drawString(txt, x, y);
        if (settingInput == 2) {
            g2.drawString("->", x - 30, y);
        }
        g2.drawString(String.valueOf(gamePanel.ghostsNum), x + 12 * gamePanel.tileSize, y);

        //bomb power
        txt = "bomb power";
        y = gamePanel.tileSize * 8;
        g2.drawString(txt, x, y);
        if (settingInput == 3) {
            g2.drawString("->", x - 30, y);
        }
        g2.drawString(String.valueOf(gamePanel.player.powerBooster), x + 12 * gamePanel.tileSize, y);

        //bomb radius
        txt = "bomb radius";
        y = gamePanel.tileSize * 10;
        g2.drawString(txt, x, y);
        if (settingInput == 4) {
            g2.drawString("->", x - 30, y);
        }
        g2.drawString(String.valueOf(gamePanel.player.radiusBooster), x + 12 * gamePanel.tileSize, y);

        //player life
        txt = "player life";
        y = gamePanel.tileSize * 12;
        g2.drawString(txt, x, y);
        if (settingInput == 5) {
            g2.drawString("->", x - 20, y);
        }
        g2.drawString(String.valueOf(gamePanel.player.getHp()), x + 12 * gamePanel.tileSize, y);
    }


    public int getCenterX(String txt) {
        int length = (int) g2.getFontMetrics().getStringBounds(txt, g2).getWidth();
        int x = gamePanel.screenWidth / 2 - length / 2;
        return x;
    }
}
