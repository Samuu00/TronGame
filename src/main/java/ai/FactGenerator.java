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

        // DEBUG: Stampiamo cosa stiamo creando
        System.out.println("--- GENERATING FACTS ---");

        try {
            GridSize gs = new GridSize(width, height);
            input.addObjectInput(gs);
            System.out.println("Fact: grid_size(" + width + "," + height + ")");

            for (Position p : positions) {
                input.addObjectInput(p);
                System.out.println("Fact: pos(" + p.getPlayerId() + "," + p.getX() + "," + p.getY() + ")");
            }

            for (Obstacle o : obstacles) {
                input.addObjectInput(o);
                // Non stamparli tutti se sono troppi, magari solo i primi 5
            }
            System.out.println("Total obstacles sent: " + obstacles.size());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return input;
    }
}