package ai;

import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResultHandler {
    private static final Pattern PATTERN = Pattern.compile("next_move\\((\\d+),\"?([a-zA-Z]+)\"?\\)");
    public Map<Integer, String> getActions(AnswerSets answerSets) {
        System.out.println("SOLVER OUTPUT: " + answerSets.getOutput());
        Map<Integer, String> decisions = new HashMap<>();

        if (answerSets == null || answerSets.getAnswersets().isEmpty()) {
            return decisions;
        }

        // Recuperiamo il primo modello stabile
        AnswerSet firstAS = answerSets.getAnswersets().get(0);

        // toString() in EmbASP restituisce la stringa degli atomi così come arrivano dal solver
        String rawOutput = firstAS.toString();

        Matcher matcher = PATTERN.matcher(rawOutput);
        while (matcher.find()) {
            try {
                int playerId = Integer.parseInt(matcher.group(1));
                String direction = matcher.group(2).toUpperCase();
                decisions.put(playerId, direction);
            } catch (Exception e) {
                System.err.println("Errore parsing atomo: " + matcher.group(0));
            }
        }

        return decisions;
    }
}