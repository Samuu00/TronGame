package ai;

import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import model.MoveDecision;

import java.util.HashMap;
import java.util.Map;

/**
 * Decoder: Interpreta i modelli stabili di ASP per ottenere le mosse di tutti i player.
 */
public class ResultHandler {

    /**
     * Estrae le mosse per tutti i giocatori dal primo AnswerSet disponibile.
     * @param answerSets Il risultato del solver.
     * @return Una mappa ID Giocatore -> Direzione ("UP", "DOWN", ecc.).
     */
    public Map<Integer, String> getActions(AnswerSets answerSets) {
        Map<Integer, String> decisions = new HashMap<>();

        // Prendiamo il primo AnswerSet (il modello stabile calcolato)
        for (AnswerSet a : answerSets.getAnswersets()) {
            try {
                for (Object obj : a.getAtoms()) {
                    if (obj instanceof MoveDecision) {
                        MoveDecision md = (MoveDecision) obj;
                        // Popoliamo la mappa: ID -> DIREZIONE
                        decisions.put(md.getPlayerId(), md.getDirection());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // In ASP Tron, solitamente analizziamo il primo modello stabile trovato
            if (!decisions.isEmpty()) break;
        }

        return decisions;
    }
}