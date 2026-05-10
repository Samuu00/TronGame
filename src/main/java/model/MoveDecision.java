package model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("next_move")
public class MoveDecision {
    @Param(0) private int playerId;
    @Param(1) private String direction;

    public MoveDecision() {}

    public int getPlayerId() { return playerId; }
    public void setPlayerId(int playerId) { this.playerId = playerId; }
    public String getDirection() { return direction; }
    public void setDirection(String direction) { this.direction = direction; }
}