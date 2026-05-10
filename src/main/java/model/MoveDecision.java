package model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("next_move")
public class MoveDecision {
    @Param(0) private int player;
    @Param(1) private String direction;

    public MoveDecision() {}

    public void setPlayer(int player) { this.player = player; }

    public void setDirection(String direction) { this.direction = direction; }

    public int getPlayer() { return player; }
    public String getDirection() { return direction; }
}