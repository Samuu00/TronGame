package model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("pos")
public class Position {
    @Param(0) private int playerId;
    @Param(1) private int x;
    @Param(2) private int y;

    public Position() {}

    public Position(int playerId, int x, int y) {
        this.playerId = playerId;
        this.x = x;
        this.y = y;
    }

    public int getPlayerId() { return playerId; }
    public void setPlayerId(int playerId) { this.playerId = playerId; }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
}