package main;

public class SimpleSkeleton extends Enemy{
    public SimpleSkeleton(GamePanel gamePanel) {
        super(gamePanel);
        this.setImages("player3");
        this.setMaxHp(3);
        this.speed=1;
        this.setName("simpleSkeleton");
        this.setMaxHp(3);
        this.setHp(this.getMaxHp());
    }

}
