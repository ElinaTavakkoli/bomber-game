import javax.swing.*;
import java.awt.*;

public class Wall extends Entity {
    public boolean collision;

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public Wall(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

}
