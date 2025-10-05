package main;

import java.awt.*;

public class Ghost extends Enemy{
    public Ghost(GamePanel gamePanel) {
        super(gamePanel);
        this.setImages("player2");
        this.speed=3;
        this.setMaxHp(1);
        this.setHp(this.getMaxHp());
        System.out.println(this.getHp());
        this.setName("ghost");
    }


}
