package main;

import objects.Bomb;
import objects.MagicalBox;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Enemy extends Entity {
    private Image up;
    private Image down;
    private Image left;
    private Image right;
    private Image idle;
    private Image fullHp = new ImageIcon("C:\\Users\\LENOVO\\IdeaProjects\\bomber\\assets\\full.png").getImage();
    private Image halfHp = new ImageIcon("C:\\Users\\LENOVO\\IdeaProjects\\bomber\\assets\\half.png").getImage();
    private Image lowHp = new ImageIcon("C:\\Users\\LENOVO\\IdeaProjects\\bomber\\assets\\low.png").getImage();
    public boolean bombing;
    int enemyCol;
    int enemyRow;

    public Enemy(GamePanel gamePanel) {
        super(gamePanel);
        this.setCurrentState(Direction.IDLE);
        collisionArea = new Rectangle(4, 0, 40, 40);
        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;
        collisionOn = true;
    }

    public void setImages(String pathname) {
        up = new ImageIcon("C:\\Users\\LENOVO\\IdeaProjects\\bomber\\assets\\images\\players\\" + pathname + "\\up.gif").getImage();
        down = new ImageIcon("C:\\Users\\LENOVO\\IdeaProjects\\bomber\\assets\\images\\players\\" + pathname + "\\down.gif").getImage();
        left = new ImageIcon("C:\\Users\\LENOVO\\IdeaProjects\\bomber\\assets\\images\\players\\" + pathname + "\\left.gif").getImage();
        right = new ImageIcon("C:\\Users\\LENOVO\\IdeaProjects\\bomber\\assets\\images\\players\\" + pathname + "\\right.gif").getImage();
        idle = new ImageIcon("C:\\Users\\LENOVO\\IdeaProjects\\bomber\\assets\\images\\players\\" + pathname + "\\down\\1.png").getImage();
    }

    public void setAction() {
        actionLockCounter++;
        if (actionLockCounter == 120) {
            Random random = new Random();
            int randomNum = random.nextInt(100) + 1;
            if (randomNum <= 25) {
                setCurrentState(Direction.UP);
            }
            if (randomNum > 25 && randomNum < 50) {
                setCurrentState(Direction.DOWN);
            }
            if (randomNum > 50 && randomNum < 75) {
                setCurrentState(Direction.LEFT);
            }
            if (randomNum > 75) {
                setCurrentState(Direction.RIGHT);
            }
            actionLockCounter = 0;
        }
    }

    public void update() {
        setAction();

        collisionOn = false;
        gamePanel.collisionHandler.checkTile(this);
        gamePanel.collisionHandler.checkPlayerCollision(this);

        //if collision is false,player can move
        if (!collisionOn) {
            switch (getCurrentState()) {
                case UP:
                    this.setY(this.getY() - speed);
                    break;
                case DOWN:
                    this.setY(this.getY() + speed);
                    break;
                case RIGHT:
                    this.setX(this.getX() + speed);
                    break;
                case LEFT:
                    this.setX(this.getX() - speed);
                    break;
            }

        }
    }

    public void draw(Graphics2D g2) {
        switch (getCurrentState()) {
            case UP:
                g2.drawImage(up, this.getX(), this.getY(), gamePanel.tileSize, gamePanel.tileSize, null);
                break;
            case DOWN:
                g2.drawImage(down, this.getX(), this.getY(), gamePanel.tileSize, gamePanel.tileSize, null);
                break;
            case RIGHT:
                g2.drawImage(right, this.getX(), this.getY(), gamePanel.tileSize, gamePanel.tileSize, null);
                break;
            case LEFT:
                g2.drawImage(left, this.getX(), this.getY(), gamePanel.tileSize, gamePanel.tileSize, null);
                break;
            case IDLE:
                g2.drawImage(idle, this.getX(), this.getY(), gamePanel.tileSize, gamePanel.tileSize, null);
                break;
        }
        if (this.getHp() == this.getMaxHp()) {
            g2.drawImage(fullHp, this.getX(), this.getY() - 2, 48, 4, null);
        } else if (this.getHp() == this.getMaxHp() - 1) {
            g2.drawImage(halfHp, this.getX(), this.getY() - 2, 48, 4, null);
        } else if (this.getHp() == this.getMaxHp() - 2) {
            g2.drawImage(lowHp, this.getX(), this.getY() - 2, 48, 4, null);
        }
    }

    public void setBounds() {
        int count = 0;
        while (count < 1) {
            int minRandomCol = 0;
            int maxRandomCol = gamePanel.maxScreenCol;
            int minRandomRow = 0;
            int maxRandomRow = gamePanel.maxScreenRow;
            int randomCol = (int) Math.floor(Math.random() * (maxRandomCol - minRandomCol + 1) + minRandomCol);
            int randomRow = (int) Math.floor(Math.random() * (maxRandomRow - minRandomRow + 1) + minRandomRow);
            for (int i = 0; i < gamePanel.maxScreenRow; i++) {
                for (int j = 0; j < gamePanel.maxScreenCol; j++) {
                    if (randomCol == j && randomRow == i && gamePanel.mapHandler.mapTileNum[j][i] == 0) {
                        this.setX(gamePanel.tileSize * randomCol);
                        this.setY(gamePanel.tileSize * randomRow);
                        count++;
                    }
                }
            }
        }
    }

    public void getEnemyBounds() {
        enemyCol = (this.getX() + 10) / 48;
        enemyRow = (this.getY() + 12) / 48;
    }

    public void bombExplosion(Bomb bomb) {
        getEnemyBounds();
        rightExplosion(bomb);
        leftExplosion(bomb);
        upExplosion(bomb);
        downExplosion(bomb);
    }

    public void rightExplosion(Bomb bomb) {
        int defaultRadius = bomb.getRadius();
        //right explosion
        if (bomb.getX() / 48 + bomb.getRadius() > gamePanel.maxScreenCol)
            bomb.setRadius(gamePanel.maxScreenCol - bomb.getX() / 48);
        for (int i = 0; i <= bomb.getRadius(); i++)
            if (gamePanel.tileManager.mapTileNum[bomb.getX() / 48 + i][bomb.getY() / 48] == 0) {
                if (enemyCol == bomb.getX() / 48 + i && enemyRow == bomb.getY() / 48) {
                    this.setHp(this.getHp() - bomb.getPower());
                }
            }else break;
        bomb.setRadius(defaultRadius);
    }

    public void leftExplosion(Bomb bomb) {
        int defaultRadius = bomb.getRadius();
        if (bomb.getX() / 48 < bomb.getRadius())
            bomb.setRadius(bomb.getX() / 48);
        for (int i = 1; i <= bomb.getRadius(); i++)
            if (gamePanel.tileManager.mapTileNum[bomb.getX() / 48 - i][bomb.getY() / 48] == 0) {
                if (enemyCol == bomb.getX() / 48 - i && enemyRow == bomb.getY() / 48) {
                    this.setHp(this.getHp() - bomb.getPower());
                }
            }else break;
        bomb.setRadius(defaultRadius);
    }

    public void upExplosion(Bomb bomb) {
        int defaultRadius = bomb.getRadius();
        if (bomb.getY() / 48 < bomb.getRadius())
            bomb.setRadius(bomb.getY() / 48);
        for (int i = 1; i <= bomb.getRadius(); i++)
            if (gamePanel.tileManager.mapTileNum[bomb.getX() / 48 ][bomb.getY() / 48- i] == 0) {
                if (enemyCol == bomb.getX() / 48 && enemyRow == bomb.getY() / 48 - i) {
                    this.setHp(this.getHp() - bomb.getPower());
                }
            }else break;
        bomb.setRadius(defaultRadius);
    }

    public void downExplosion(Bomb bomb) {
        int defaultRadius = bomb.getRadius();
        if (bomb.getY() / 48 + bomb.getRadius() > gamePanel.maxScreenRow)
            bomb.setRadius(gamePanel.maxScreenRow - bomb.getY() / 48);
        for (int i = 1; i <= bomb.getRadius(); i++)
            if (gamePanel.tileManager.mapTileNum[bomb.getX() / 48 ][bomb.getY() / 48+ i] == 0) {
                if (enemyCol == bomb.getX() / 48 && enemyRow == bomb.getY() / 48 + i) {
                    this.setHp(this.getHp() - bomb.getPower());
                }
            }else break;
        bomb.setRadius(defaultRadius);
    }
}
