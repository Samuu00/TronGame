package ai;

import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import model.GridSize;
import model.Position;
import java.util.List;

/**
 * Traduce lo stato del gioco in fatti per ASP.
 */
public class FactGenerator {

    public ASPInputProgram getFacts(int width, int height, List<Position> positions) {
        ASPInputProgram input = new ASPInputProgram();

        try {
            // Definisci i bordi del campo
            input.addObjectInput(new GridSize(width, height));

            // Inserisci le posizioni di tutte le moto presenti
            for (Position p : positions) {
                input.addObjectInput(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return input;
    }
}