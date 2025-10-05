package main;

import objects.Bomb;

import java.awt.*;

public class HoodedSkeleton extends Enemy{
    public HoodedSkeleton(GamePanel gamePanel) {
        super(gamePanel);
        this.setImages("player4");
        this.speed=2;
        this.setName("hoodedSkeleton");
        this.setMaxHp(1);
        this.setHp(this.getMaxHp());
    }

    private Bomb bomb;

    int bombTimer=0;

    @Override
    public void update() {
        super.update();
        bombTimer++;
        if(bombTimer==(8*60)){
            bomb=new Bomb(this.getX(),this.getY(),gamePanel);
            bomb.setType("enemyBomb");
            bombing=true;
            bombTimer=0;
        }
        if(bombing){
            bomb.update();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        if(bombing){
            bomb.draw(g2);
        }

    }
}
