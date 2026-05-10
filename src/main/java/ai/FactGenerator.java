package ai;

import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import model.GridSize;
import model.Position;
import model.Obstacle;
import java.util.List;

/**
 * Traduce lo stato del gioco in fatti per ASP.
 */
public class FactGenerator {

    public ASPInputProgram getFacts(int width, int height, List<Position> positions, List<Obstacle> obstacles) {
        ASPInputProgram input = new ASPInputProgram();
        try {
            input.addObjectInput(new GridSize(width, height));
            for (Position p : positions) input.addObjectInput(p);
            for (Obstacle o : obstacles) input.addObjectInput(o);
        } catch (Exception e) { e.printStackTrace(); }
        return input;
    }
}