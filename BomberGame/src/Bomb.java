import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bomb extends Entity implements ActionListener {
    public Bomb(int x, int y, int height, int width, int power, int coolDown, int radius, int countDown) {
        super(x, y, height, width);
        this.power = power;
        this.coolDown = coolDown;
        this.radius = radius;
        this.countDown = countDown;
    }

    private int countDown;
    private int power;
    private int coolDown;
    private int radius;
    private float timeLapse = 0;

    public void setEnemyBomb(boolean enemyBomb) {
        this.enemyBomb = enemyBomb;
    }

    private float timeRemaining = countDown;
    private final int coreValue = 6;
    private int radiusValue;
    private final int UpRadiusValue = 5;
    private final int LeftRadiusValue = 7;
    private float green = (0.25f) * timeRemaining;
    private float red = 1 - green;
    private int timer = 0;

    private boolean enemyBomb;
    private Image bomb = new ImageIcon("C:\\Users\\Lenovo\\Downloads\\assets\\assets\\images\\bomb\\1.png").getImage();
    private Image BOMB = new ImageIcon("C:\\Users\\Lenovo\\Downloads\\assets\\assets\\images\\6l40f9.gif").getImage();
    private Image core = new ImageIcon("C:\\Users\\Lenovo\\Downloads\\assets\\assets\\images\\explosion\\1\\core.png").getImage();
    private Image left = new ImageIcon("C:\\Users\\Lenovo\\Downloads\\assets\\assets\\images\\explosion\\1\\left.png").getImage();
    private Image right = new ImageIcon("C:\\Users\\Lenovo\\Downloads\\assets\\assets\\images\\explosion\\1\\right.png").getImage();
    private Image down = new ImageIcon("C:\\Users\\Lenovo\\Downloads\\assets\\assets\\images\\explosion\\1\\down.png").getImage();
    private Image up = new ImageIcon("C:\\Users\\Lenovo\\Downloads\\assets\\assets\\images\\explosion\\1\\up.png").getImage();
    private Image image;

    public Image getBomb() {
        return bomb;
    }

    public int getPower() {
        return power;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public float getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public float getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public int getUpRadiusValue() {
        return UpRadiusValue;
    }

    public int getLeftRadiusValue() {
        return LeftRadiusValue;
    }

    public int getCoreValue() {
        return coreValue;
    }

    @Override
    public void draw(Graphics g, Image image) {
        if (timeRemaining > 0) {
            g.drawImage(bomb, x, y, width, height, null);
            g.setColor(new Color(red, green, 0));
            g.fillRect(x, y + height, (int) timeRemaining * 25, 10);
        }
        if (timeLapse == 0)
            for (int i = 0; i < 14; i++)
                for (int j = 0; j < 22; j++) {
                    if (World.tile[i][j] == coreValue)
                        g.drawImage(core, x, y, width, height, null);
                    else if (World.tile[i][j] == UpRadiusValue)
                        g.drawImage(up, j * 50, i * 50, getWidth(), getHeight(), null);
                    else if (World.tile[i][j] == LeftRadiusValue) {
                        g.drawImage(left, j * 50, i * 50, getWidth(), getHeight(), null);
                    } else if (World.tile[i][j] >= 30) {
                        World.tile[i][j] -= 30;
                    }
                }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (timeRemaining >= 0) {
            timeLapse += 0.5f;
            timeRemaining = coolDown - timeLapse;
        }
    }

    public void explosion() {
        int bombXIndex = (this.x + this.width / 2) / 50;
        int bombYIndex = (this.y + this.height / 2) / 50;
        int radiusX = 0, radiusY = 0;
        World.tile[bombYIndex][bombXIndex] = coreValue;
        Timer timer1 = new Timer(500, this);
        timer1.start();
        for (int counter = 1; counter <= radius; counter++) {
            for (int direction = 1; direction <= 4; direction++) {
                switch (direction) {
                    case 1:
                        radiusValue = UpRadiusValue;
                        radiusX = bombXIndex;
                        radiusY = bombYIndex - counter;
                        break;
                    case 2:
                        radiusValue = LeftRadiusValue;
                        radiusX = bombXIndex + counter;
                        radiusY = bombYIndex;
                        break;
                    case 3:
                        radiusValue = UpRadiusValue;
                        radiusX = bombXIndex;
                        radiusY = bombYIndex + counter;
                        break;
                    case 4:
                        radiusValue = LeftRadiusValue;
                        radiusX = bombXIndex - counter;
                        radiusY = bombYIndex;
                        break;
                }
                if (radiusY >= 0 && radiusY < 13 && radiusX >= 0 && radiusX < 21) {
                    switch (World.tile[radiusY][radiusX]) {
                        case 0:
                            World.tile[radiusY][radiusX] = radiusValue;
                            if (!enemyBomb)
                                AABBEnemy(radiusX * 50, radiusY * 50);
                            AABBPlayer(radiusX * 50, radiusY * 50);
                            break;
                        case 1:
                            check(direction, radiusX, radiusY);
                            World.tile[radiusY][radiusX] = radiusValue;
                            for (NormalWall normalWall : World.normalWalls)
                                if (normalWall.getX() / 50 == radiusX)
                                    if (normalWall.getY() / 50 == radiusY) {
                                        World.normalWalls.remove(normalWall);
                                        break;
                                    }
                            break;
                        case 2:
                            check(direction, radiusX, radiusY);
                            for (AmazingWall amazingWall : World.amazingWalls)
                                if (amazingWall.getX() / 50 == radiusX)
                                    if (amazingWall.getY() / 50 == radiusY) {
                                        if (amazingWall.getHP() > 0)
                                            amazingWall.setHP(amazingWall.getHP() - 1);
                                        if (amazingWall.getHP() == 0) {
                                            World.tile[radiusY][radiusX] = 0;
                                            LuckyBox luckyBox = new LuckyBox(amazingWall.getX(), amazingWall.getY(), 50, 50);
                                            World.luckyBoxes.add(luckyBox);
                                            World.amazingWalls.remove(amazingWall);
                                        }
                                        break;
                                    }
                            break;
                        case 3:
                            check(direction, radiusX, radiusY);
                            break;
                    }
                }
            }


        }
    }

    private void AABBEnemy(int x, int y) {
        for (Enemy enemy : World.enemies)
            if (enemy.getX() + enemy.getWidth() >= x && enemy.getY() + enemy.getHeight() >= y && x + this.width >= enemy.getX() && y + this.height >= enemy.getY()) {
                enemy.setHP(enemy.getHP() - this.power);
                if (enemy.getHP() <= 0)
                    World.enemies.remove(enemy);
                break;
            }
    }

    private void AABBPlayer(int x, int y) {
        if (World.player.getX() + World.player.getWidth() >= x && World.player.getY() + World.player.getHeight() >= y && x + this.width >= World.player.getX() && y + this.height >= World.player.getY()) {
            World.player.setHP(World.player.getHP() - this.power);
            if (World.player.getHP() <= 0) {
                World.player.setDead(true);
            }
        }
    }

    private void check(int state, int xIndex, int yIndex) {
        switch (state) {
            case 1:
                for (int counter = 1; counter < radius; counter++)
                    if (yIndex - counter > 0)
                        World.tile[yIndex - counter][xIndex] += World.defensiveValue;
                break;
            case 2:
                for (int counter = 1; counter < radius; counter++)
                    if (xIndex + counter < 21)
                        World.tile[yIndex][xIndex + counter] += World.defensiveValue;
                break;
            case 3:
                for (int counter = 1; counter < radius; counter++)
                    if (yIndex + counter < 13)
                        World.tile[yIndex + counter][xIndex] += World.defensiveValue;
                break;
            case 4:
                for (int counter = 1; counter < radius; counter++)
                    if (xIndex - counter > 0)
                        World.tile[yIndex][xIndex - counter] += World.defensiveValue;
                break;
        }
    }
}


