package main;

import maps.MapHandler;
import objects.Bomb;
import objects.Heart;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Player extends Entity {

    int radiusBooster = 1;
    public int powerBooster = 1;
    Bomb[] bombs = new Bomb[5];
    public int bombCount = 0;
    KeyHandler keyHandler;
    Heart heart = new Heart(gamePanel, this);

    private Image up = new ImageIcon("C:\\Users\\LENOVO\\IdeaProjects\\bomber\\assets\\images\\players\\player1\\up.gif.gif").getImage();
    private Image down = new ImageIcon("C:\\Users\\LENOVO\\IdeaProjects\\bomber\\assets\\images\\players\\player1\\down.gif.gif").getImage();
    private Image left = new ImageIcon("C:\\Users\\LENOVO\\IdeaProjects\\bomber\\assets\\images\\players\\player1\\left.gif.gif").getImage();
    private Image right = new ImageIcon("C:\\Users\\LENOVO\\IdeaProjects\\bomber\\assets\\images\\players\\player1\\right.gif.gif").getImage();
    private Image idle = new ImageIcon("C:\\Users\\LENOVO\\IdeaProjects\\bomber\\assets\\images\\players\\player1\\idle.png").getImage();
    private MapHandler map;

    public Player(GamePanel gamePanel, KeyHandler keyHandler, MapHandler map) {
        super(gamePanel);
        this.keyHandler = keyHandler;
        this.map = map;
        this.setX(50);
        this.setY(50);
        speed = 4;
        this.setMaxHp(4);
        this.setHp(this.getMaxHp() - 1);
        setCurrentState(Direction.IDLE);
        collisionArea = new Rectangle(10, 12, 28, 22);
        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;
    }

    public void update() {

        if (keyHandler.isUpPressed()) {
            setCurrentState(Direction.UP);
        } else if (keyHandler.isDownPressed()) {
            setCurrentState(Direction.DOWN);

        } else if (keyHandler.isLeftPressed()) {
            setCurrentState(Direction.LEFT);

        } else if (keyHandler.isRightPressed()) {
            setCurrentState(Direction.RIGHT);
        } else {
            setCurrentState(Direction.IDLE);
        }
        if (keyHandler.isBombed()) {
            if (bombCount <= 3) {
                bombs[bombCount] = new Bomb(this.getX(), this.getY(), gamePanel);
                bombs[bombCount].setRadius(radiusBooster);
                bombs[bombCount].setPower(powerBooster);
                bombs[bombCount].setType("playerBomb");
                bombs[bombCount].coolDown = true;
                bombCount++;
            } else
                System.out.println("you have no bomb");
            keyHandler.setBomb = false;
        }


        //check tile collision
        collisionOn = false;
        gamePanel.collisionHandler.checkTile(this);

        //check enemy collision
        int enemyIndex = gamePanel.collisionHandler.checkEnemyCollision(this, gamePanel.enemies);
        interactEnemy(enemyIndex);

        //check object collision
        int objectIndex = gamePanel.collisionHandler.checkObjectCollision(this, gamePanel.tileManager.magicalBox);
        pickUpObject(objectIndex);


        //if collisionOn is false,player can move
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

        //BOMB UPDATING
        for (int i = 0; i <= 4; i++)
            if (bombs[i] != null) {
                if (!bombs[i].isExploded()) {
                        bombs[i].update();
                }
            }

        for (int i = 0; i < 4; i++)
            if (bombs[i] != null) {
                if (bombs[i].coolDown) {
                    bombs[i].coolDownTime();
                }
            }

        for (int i = 0; i < gamePanel.enemies.length; i++) {
            if (gamePanel.enemies[i] != null)
                if (gamePanel.enemies[i].getHp() <= 0) {
                    gamePanel.enemies[i] = null;
                }
        }

        //HEART UPDATING
        heart.update();


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

        for (int i = 0; i <= bombCount; i++)
            if (bombs[i] != null) {
                if (!bombs[i].isExploded()) {
                    bombs[i].draw(g2);
                }
            }
        //HEART DRAWING
        heart.draw(g2);

    }

    public void pickUpObject(int index) {
        if (index != 100) {
            if (Objects.equals(gamePanel.tileManager.magicalBox[index].getType(), "heartPotion")) {
                if (this.getHp() != this.getMaxHp()) {
                    this.setHp(this.getHp() + 1);
                }
            } else if (Objects.equals(gamePanel.tileManager.magicalBox[index].getType(), "bombRadiusBooster")) {
                radiusBooster++;

            } else if (Objects.equals(gamePanel.tileManager.magicalBox[index].getType(), "bombPowerBooster")) {
                powerBooster++;
            }
            gamePanel.tileManager.magicalBox[index] = null;
        }
    }


    public void interactEnemy(int index) {
        if (index != 100) {
            this.setHp(this.getHp() - 1);
            gamePanel.enemies[index].setHp(0);
        }
    }

}