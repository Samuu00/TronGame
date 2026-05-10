package model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("obstacle")
public class Obstacle {
    @Param(0) private int x;
    @Param(1) private int y;
    private int ownerId;

    public Obstacle() {}

    public Obstacle(int x, int y, int ownerId) {
        this.x = x;
        this.y = y;
        this.ownerId = ownerId;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getOwnerId() { return ownerId; }
}