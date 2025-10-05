import javax.swing.*;
import java.awt.*;

public class LuckyBox extends Entity{
    public LuckyBox(int x, int y, int height, int width) {
        super(x, y, height, width);
    }
    private   Image image = new ImageIcon("C:\\Users\\Lenovo\\Downloads\\assets\\assets\\images\\box1.jpg").getImage();

    public  Image getImage() {
        return image;
    }

}
