package main;

import main.GamePanel;

import java.awt.*;

public class Entity {
    private String name;
    private int x, y;
    private int width, height;
    public Rectangle collisionArea;
    public boolean collisionOn = false;
    int collisionAreaDefaultX,collisionAreaDefaultY;
    int actionLockCounter=0;
    private int maxHp=4;
    private int hp;
    int speed;
    public void update() {
    }

    enum Direction {
        UP, DOWN, RIGHT, LEFT, IDLE
    }

    private Direction currentState;
    GamePanel gamePanel;
    public Entity(GamePanel gamePanel) {
        this.gamePanel=gamePanel;
    }

    public void setCurrentState(Direction currentState) {
        this.currentState = currentState;
    }

    public Direction getCurrentState() {
        return currentState;
    }

    public void draw(Graphics2D g2) {

    }

    public void setAction(){

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getHp() {
        return hp;
    }
}
