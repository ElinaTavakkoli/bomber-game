import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.Timer;

public class Person extends Entity implements Movable, ActionListener {
    protected Image IDLE;
    protected boolean blowUp = false;
    protected int speed;
    protected int HP;
    protected int bombCOUNTER;
    protected int bombCoolDown;
    protected int bombRadius;
protected int temp = bombCOUNTER;
    protected boolean enemy;

    public Person(int x, int y, int height, int width, int speed, int HP, int bombCOUNTER, int  bombRadius, int bombCoolDown, int bombsPower, int bombCountDown) {
        super(x, y, height, width);
        this.speed = speed;
        this.HP = HP;
        this.bombCOUNTER = bombCOUNTER;
        this.bombCoolDown = bombCoolDown;
        this.bombsPower = bombsPower;
        this.bombCountDown = bombCountDown;
        this.bombRadius = bombRadius;
    }


    enum Direction {
        UP, DOWN, LEFT, RIGHT, IDLE
    }

    Player.Direction currentState = Player.Direction.IDLE;

    protected Vector<Bomb> bombs = new Vector<>();

    public boolean isEnemy() {
        return enemy;
    }

    public void setEnemy(boolean enemy) {
        this.enemy = enemy;
    }

    public Vector<Bomb> getBombs() {
        return bombs;
    }
    protected int bombsPower;
    protected int bombCountDown;

    public int getSpeed() {
        return speed;
    }

    public int getBombCountDown() {
        return bombCountDown;
    }

    public void setBombCountDown(int bombCountDown) {
        this.bombCountDown = bombCountDown;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getBombsPower() {
        return bombsPower;
    }

    public int getBombRadius() {
        return bombRadius;
    }

    public void setBombRadius(int bombRadius) {
        this.bombRadius = bombRadius;
    }

    public void setBombsPower(int bombsPower) {
        this.bombsPower = bombsPower;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    @Override
    public void move(Direction direction) {
        switch (direction) {
            case UP -> {
                if (canMoveUp(getX(), getY() - speed, getWidth())) {
                    setY(getY() - speed);
                    currentState = Player.Direction.UP;
                    break;
                }
            }
            case DOWN -> {
                if (canMoveDown(getX(), getY() + speed, getWidth(), getHeight())) {
                    setY(getY() + speed);
                    currentState = Player.Direction.DOWN;
                    break;
                }
            }
            case LEFT -> {
                if (canMoveLeft(getX() - speed, getY(), getHeight())) {
                    setX(getX() - speed);
                    currentState = Player.Direction.LEFT;
                    break;
                }
            }
            case RIGHT -> {
                if (canMoveRight(getX() + speed, getY(), getWidth(), getHeight())) {
                    setX(getX() + speed);
                    currentState = Player.Direction.RIGHT;
                    break;
                }
            }
        }
    }

    public Image getIDLE() {
        return IDLE;
    }

    protected boolean canMoveUp(int x, int y, int width) {
        if (!isSolid(x, y))
            if (!isSolid(x + width - 1, y))
                return true;
        return false;
    }

    protected boolean canMoveDown(int x, int y, int width, int height) {
        if (!isSolid(x + width - 1, y + height - 1))
            if (!isSolid(x, y + height - 1))
                return true;
        return false;
    }

    protected boolean canMoveLeft(int x, int y, int height) {
        if (!isSolid(x, y))
            if (!isSolid(x, y + height - 1))
                return true;
        return false;
    }

    protected boolean canMoveRight(int x, int y, int width, int height) {
        if (!isSolid(x + width - 1, y))
            if (!isSolid(x + width - 1, y + height - 1))
                return true;
        return false;
    }

    private boolean isSolid(int x, int y) {
        int xIndex = x / 50;
        int yIndex = y / 50;
        int value = World.tile[yIndex][xIndex];
        if (value != 3 && value != 2 && value != 1)
            return false;
        return true;
    }

    public void setBombCONTER(int bombCONTER) {
        this.bombCOUNTER = bombCONTER;
    }

    public int getBombCONTER() {
        return bombCOUNTER;
    }

    protected void blowUp() {
        if(bombCOUNTER>0) {
            this.blowUp = true;
            setBombCONTER(getBombCONTER() - 1);
            Bomb bomb = new Bomb(x, y, height, width, bombsPower, bombCoolDown, bombRadius, bombCountDown);
            bomb.setEnemyBomb(enemy);
            bombs.add(bomb);
            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    bomb.explosion();
                }
            });
            timer.setRepeats(false); // Only execute once
            timer.start();
            Timer timer1 = new Timer((bomb.getCoolDown()) * 1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    for (int i = 0; i < 22; i++)
                        for (int j = 0; j < 14; j++)
                            if (World.tile[j][i] == bomb.getLeftRadiusValue() || World.tile[j][i] == bomb.getUpRadiusValue() || World.tile[j][i] == bomb.getCoreValue())
                                World.tile[j][i] = 0;
                    bombs.remove(bomb);
                    setBombCONTER(getBombCONTER() + 1);
                    System.out.println("llllllllllllllllllll" + bombCOUNTER);
                }
            });
            timer1.setRepeats(false); // Only execute once
            timer1.start();

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public boolean isExplosion() {
        return blowUp;
    }

    public void setBlowUp(boolean blowUp) {
        this.blowUp = blowUp;
    }
}
