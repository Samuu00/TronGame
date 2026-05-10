package controller;

import javafx.animation.AnimationTimer;
import ai.AspOrchestrator;
import model.Obstacle;
import model.Position;
import model.MoveDecision;
import view.GameView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class GameController {
    private final AspOrchestrator aiOrchestrator;
    private final int width = 30;
    private final int height = 30;
    private final GameView view;

    // Gestiamo i 4 player come una mappa ID -> Posizione attuale
    private Map<Integer, Position> players = new HashMap<>();
    // Mappa per le direzioni correnti di ogni moto
    private Map<Integer, String> currentDirections = new HashMap<>();

    private final List<Obstacle> trails = new ArrayList<>();

    public GameController(GameView view, int size) {
        this.view = view;
        this.aiOrchestrator = new AspOrchestrator("lib/dlv2.exe");

        // Inizializzazione 4 moto ai 4 angoli
        players.put(1, new Position(1, 2, 2));        currentDirections.put(1, "RIGHT");
        players.put(2, new Position(2, 27, 2));       currentDirections.put(2, "LEFT");
        players.put(3, new Position(3, 2, 27));       currentDirections.put(3, "RIGHT");
        players.put(4, new Position(4, 27, 27));      currentDirections.put(4, "LEFT");
    }

    public void startGame() {
        new AnimationTimer() {
            private long lastUpdate = 0;
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 200_000_000) {
                    update();
                    lastUpdate = now;
                }
            }
        }.start();
    }

    private void update() {
        if (players.isEmpty()) return;

        // Prima di muovere, la posizione attuale diventa un ostacolo (scia)
        for (Position p : players.values()) {
            trails.add(new Obstacle(p.getX(), p.getY(), p.getPlayerId()));
        }

        // Chiedi all'IA
        Map<Integer, String> nextMoves = aiOrchestrator.computeNextMoves(width, height,
                new ArrayList<>(players.values()), trails);

        // Raccogliamo i player da eliminare (nessuna mossa sicura)
        List<Integer> toRemove = new ArrayList<>();

        // Muovi i player
        for (Integer id : new ArrayList<>(players.keySet())) {
            if (nextMoves.containsKey(id)) {
                String move = nextMoves.get(id);
                movePlayer(id, move);
            } else {
                // Se l'IA non ha trovato mosse sicure, la moto è morta
                System.out.println("Player " + id + " has no safe moves! Eliminated.");
                toRemove.add(id);
            }
        }

        // Controlla collisioni dopo il movimento
        checkCollisions(toRemove);

        // Rimuovi i player eliminati
        for (Integer id : toRemove) {
            players.remove(id);
            currentDirections.remove(id);
        }

        // Rendering
        view.renderAll(new ArrayList<>(players.values()), trails, width, height);

        // Check game over
        if (players.size() <= 1) {
            if (players.size() == 1) {
                int winnerId = players.keySet().iterator().next();
                System.out.println("🏆 PLAYER " + winnerId + " WINS!");
            } else {
                System.out.println("DRAW! All players eliminated.");
            }
            view.showGameOver();
        }
    }

    private void movePlayer(int id, String dir) {
        Position p = players.get(id);
        if (p == null) return;

        switch (dir.toUpperCase()) {
            case "UP"    -> p.setY(p.getY() - 1);
            case "DOWN"  -> p.setY(p.getY() + 1);
            case "LEFT"  -> p.setX(p.getX() - 1);
            case "RIGHT" -> p.setX(p.getX() + 1);
        }
    }

    private void checkCollisions(List<Integer> toRemove) {
        for (Map.Entry<Integer, Position> entry : players.entrySet()) {
            int id = entry.getKey();
            Position p = entry.getValue();

            // Collisione con i bordi
            if (p.getX() < 0 || p.getX() >= width || p.getY() < 0 || p.getY() >= height) {
                System.out.println("PLAYER " + id + " HIT THE WALL!");
                if (!toRemove.contains(id)) toRemove.add(id);
                continue;
            }

            // Collisione con le scie (escludendo quella appena aggiunta dal player stesso)
            for (Obstacle t : trails) {
                if (t.getX() == p.getX() && t.getY() == p.getY()) {
                    System.out.println("PLAYER " + id + " HIT A TRAIL!");
                    if (!toRemove.contains(id)) toRemove.add(id);
                    break;
                }
            }
        }
    }
}