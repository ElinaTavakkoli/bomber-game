import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Player extends Person {
    private Image UP = new ImageIcon("C:\\Users\\Lenovo\\IdeaProjects\\BomberGame\\src\\up.gif").getImage();
    private Image DOWN = new ImageIcon("C:\\Users\\Lenovo\\IdeaProjects\\BomberGame\\src\\down.gif").getImage();
    private Image LEFT = new ImageIcon("C:\\Users\\Lenovo\\IdeaProjects\\BomberGame\\src\\left.gif").getImage();
    private Image RIGHT = new ImageIcon("C:\\Users\\Lenovo\\IdeaProjects\\BomberGame\\src\\right.gif").getImage();
    private Image IDLE = new ImageIcon("C:\\Users\\Lenovo\\IdeaProjects\\BomberGame\\src\\down.gif").getImage();
    protected int bombCOUNTER = 4;
    private boolean GameOver = false;

    private boolean dead = false;

    public int getBombCOUNTER() {
        return bombCOUNTER;
    }

    public void setBombCOUNTER(int bombCOUNTER) {
        this.bombCOUNTER = bombCOUNTER;
    }

    public boolean isGameOver() {
        return GameOver;
    }

    public void setGameOver(boolean gameOver) {
        GameOver = gameOver;
    }

    public Player(int x, int y, int height, int width, int speed, int HP, int bombCOUNTER, int bombRadius, int bombCoolDown, int bombsPower, int bombCountDown) {
        super(x, y, height, width, speed,HP, bombCOUNTER, bombRadius, bombCoolDown, bombsPower, bombCountDown);
        setEnemy(false);
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    Direction currentState = Direction.IDLE;

    @Override
    public void move(Direction direction) {
        switch (direction) {
            case UP -> {
                if (canMoveUp(getX(), getY() - speed, getWidth())) {
                    setY(getY() - speed);
                    currentState = Direction.UP;
                    break;
                }
            }
            case DOWN -> {
                if (canMoveDown(getX(), getY() + speed, getWidth(), getHeight())) {
                    setY(getY() + speed);
                    currentState = Direction.DOWN;
                    break;
                }
            }
            case LEFT -> {
                if (canMoveLeft(getX() - speed, getY(), getHeight())) {
                    setX(getX() - speed);
                    currentState = Direction.LEFT;
                    break;
                }
            }
            case RIGHT -> {
                if (canMoveRight(getX() + speed, getY(), getWidth(), getHeight())) {
                    setX(getX() + speed);
                    currentState = Direction.RIGHT;
                    break;
                }
            }
        }
    }

    @Override
    public void draw(Graphics g, Image image) {
        switch (currentState) {
            case UP: {
                super.draw(g, UP);
                break;
            }
            case DOWN: {
                super.draw(g, DOWN);
                break;
            }
            case LEFT: {
                super.draw(g, LEFT);
                break;
            }
            case RIGHT: {
                super.draw(g, RIGHT);
                break;
            }
            case IDLE: {
                super.draw(g, IDLE);
                break;
            }
        }
    }

    public void AABB() {
        for (Enemy enemy : World.enemies)
            if (enemy.getX() + enemy.getWidth() >= this.x && enemy.getY() + enemy.getHeight() >= this.y && this.x + this.width >= enemy.getX() && this.y + this.height >= enemy.getY()){
                setHP(HP-1);
                World.enemies.remove(enemy);
                World.hSkeletons.remove(enemy);
            break;
            }
        for (LuckyBox luckyBox : World.luckyBoxes)
            if (luckyBox.getX() + luckyBox.getWidth() >= this.x && luckyBox.getY() + luckyBox.getHeight() >= this.y && this.x + this.width >= luckyBox.getX() && this.y + this.height >= luckyBox.getY()) {
                randomGift();
                World.luckyBoxes.remove(luckyBox);
                break;
            }
    }

    private void randomGift() {
        Random random = new Random();
        int gift = random.nextInt(3);
        switch (gift) {
            case 0:
                this.HP++;
                break;
            case 1:
                setBombsPower(bombsPower+1);
                break;
            case 2:
                setBombRadius(bombRadius+1);
                break;
        }
    }

    public void setDead(boolean dead) {
        this.dead=dead;
    }
}
