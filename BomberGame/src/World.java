import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class World {
    public static int soulCounter = 3;
    public static int skeletonCounter = 2;
    public static int hSkeletonCounter = 1;
    public static Image SOUL = new ImageIcon("C:\\Users\\Lenovo\\IdeaProjects\\BomberGame\\src\\soul.jpg").getImage();
    public static Image SKELETON = new ImageIcon("C:\\Users\\Lenovo\\IdeaProjects\\BomberGame\\src\\skelton1.png").getImage();
    public static Image hSKELETON = new ImageIcon("C:\\Users\\Lenovo\\IdeaProjects\\BomberGame\\src\\skelton.jpg").getImage();
    public Image hardWallImage = new ImageIcon("C:\\Users\\Lenovo\\IdeaProjects\\BomberGame\\src\\wallhard.png").getImage();
    public Image normalWallImage = new ImageIcon("C:\\Users\\Lenovo\\IdeaProjects\\BomberGame\\src\\normalwall.png").getImage();
    public Image amazingWallImage = new ImageIcon("C:\\Users\\Lenovo\\IdeaProjects\\BomberGame\\src\\amazingwall.png").getImage();

    public Image hp = new ImageIcon("C:\\Users\\Lenovo\\BIdeaProjects\\bomberGame\\src\\hp.png").getImage();
    public static Player player = new Player(1000, 50, 45, 45, 50, 3, 4, 2, 2, 1, 3);///////////////////////////////
    Random randomPosition = new Random();
    public static ArrayList<Enemy> enemies = new ArrayList<>();
    public static ArrayList<AmazingWall> amazingWalls = new ArrayList<>();
    public static ArrayList<NormalWall> normalWalls = new ArrayList<>();
    public static ArrayList<HardWall> hardWalls = new ArrayList<>();
    public static ArrayList<LuckyBox> luckyBoxes=new ArrayList<>();

    public static ArrayList<Enemy> hSkeletons = new ArrayList<>();
    static Random rand = new Random();
    public static int[][] tile = new int[14][22];
    public static int defensiveValue = 30;
public boolean gameOver = false;

    public boolean isGameOver() {
        return gameOver;
    }

//    public void setGameOver(boolean gameOver) {
//        if(player.isDead())
//        this.gameOver = gameOver;
//    }

    public static int getSoulCounter() {
        return soulCounter;
    }

    public static void setSoulCounter(int soulCounter) {
        World.soulCounter = soulCounter;
    }

    public static int getSkeletonCounter() {
        return skeletonCounter;
    }

    public static void setSkeletonCounter(int skeletonCounter) {
        World.skeletonCounter = skeletonCounter;
    }

    public static int gethSkeletonCounter() {
        return hSkeletonCounter;
    }

    public static void sethSkeletonCounter(int hSkeletonCounter) {
        World.hSkeletonCounter = hSkeletonCounter;
    }

    public void drawWall(Graphics g) {
        for (HardWall hardWall : hardWalls)
            hardWall.draw(g, hardWallImage);
        for (AmazingWall amazingWall : amazingWalls)
            amazingWall.draw(g, amazingWallImage);
        for (NormalWall normalWall : normalWalls)
            normalWall.draw(g, normalWallImage);
    }

    public static void setHardWall() {
        for (int i = 0; i < 14; i++)
            for (int j = 0; j < 22; j++) {
                if (j == 0 || i == 0 || i == 13 || j == 21) {
                    tile[i][j] = 3;
                    HardWall hardWall = new HardWall(j * 50, i * 50, 50, 50);
                    hardWalls.add(hardWall);
                }
                if (i % 2 == 0 && j % 2 == 0 ) {
                    tile[i][j] = 3;
                    HardWall hardWall = new HardWall(j * 50, i * 50, 50, 50);
                    hardWalls.add(hardWall);
                }
            }
    }

    public static void setAmazingWall() {
        int counter = 1, i, j;
        while (counter <= 20) {
            i = rand.nextInt(14);
            j = rand.nextInt(22);
            if (tile[i][j] == 0  &&j != 20 && i != 1) {
                tile[i][j] = 2;
                AmazingWall amazingWall = new AmazingWall(j * 50, i * 50, 50, 50);
                amazingWalls.add(amazingWall);
                counter++;
            }
        }
    }

    public static void setNormalWall() {
        int counter = 1, i, j;
        while (counter <= 50) {
            i = rand.nextInt(14);
            j = rand.nextInt(22);
            if (tile[i][j] == 0  && j != 20 && i != 1) {
                tile[i][j] = 1;
                NormalWall normalWall = new NormalWall(j * 50, i * 50, 50, 50);
                normalWalls.add(normalWall);
                counter++;
            }
        }
    }

    public static void setEnemies() {
        setEnemy(soulCounter, 3, 1, 0, 0,0 , 0, 0, SOUL);
        setEnemy(skeletonCounter, 2, 1,0, 0,0, 0, 0, SKELETON);
        setEnemy(hSkeletonCounter, 1, 3, 1, 5,5, 4, 2, hSKELETON);
    }

    private static void setEnemy(int enemyCounter, int speed, int HP, int bombCounter,int bombRadius, int bombCoolDown, int bombPower, int bombCountDown, Image image) {
        int counter = 1;
        while (counter <= enemyCounter) {
            int i = rand.nextInt(7);
            int j = rand.nextInt(11);
            if (tile[i][j] == 0) {
                Enemy enemy = new Enemy(j * 50, i * 50, 45, 45, speed, HP, bombCounter, bombRadius, image, bombCoolDown, bombPower, bombCountDown);
                enemies.add(enemy);
                if(image==hSKELETON)
                    hSkeletons.add(enemy);
                counter++;
            }
        }
    }

    public void moveEnemies() {

        for (Enemy enemy : enemies) {
            if (enemy.getLastTimeMoved() + 1000 >= System.currentTimeMillis()) {
                enemy.move(enemy.currentState);
                enemy.setChangePosition(false);
                continue;
            }
            enemy.setChangePosition(true);
            int position = randomPosition.nextInt(4);
            switch (position) {
                case 0:
                    enemy.currentState = Person.Direction.UP;
                    break;
                case 1:
                    enemy.currentState = Person.Direction.RIGHT;
                    break;
                case 2:
                    enemy.currentState = Person.Direction.DOWN;
                    break;
                case 3:
                    enemy.currentState = Person.Direction.LEFT;

            }
            enemy.move(enemy.currentState);
        }
    }
}
