import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Enemy extends Person implements ActionListener{
    public Enemy(int x, int y, int height, int width, int speed, int HP, int bombCOUNTER, int bombRadius, Image image, int bombCoolDown, int bombsPower, int bombCountDown) {
        super(x, y, height, width, speed, HP, bombCOUNTER, bombRadius, bombCoolDown, bombsPower, bombCountDown);
        this.IDLE = image;
        setEnemy(true);
    }

    private int position;
   private long lastTimeMoved =  System.currentTimeMillis();
private boolean changePosition;

    public boolean isChangePosition() {
        return changePosition;
    }

    public void setChangePosition(boolean changePosition) {
        this.changePosition = changePosition;
    }

    public long getLastTimeMoved() {
        return lastTimeMoved;
    }

    public void setLastTimeMoved(int lastTimeMoved) {
        this.lastTimeMoved = lastTimeMoved;
    }

    @Override
    public void move(Direction direction) {
        if (changePosition)
            lastTimeMoved=  System.currentTimeMillis();
        super.move(direction);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
