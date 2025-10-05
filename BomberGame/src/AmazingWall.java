import javax.swing.*;
import java.awt.*;

public class AmazingWall extends Wall {

    private int HP = 3;



    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public AmazingWall(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
}

