package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import model.Obstacle;
import model.Position;
import java.util.List;

public class GameView {

    private final Canvas canvas;
    private final GraphicsContext gc;
    private final int cellSize;

    public GameView(double windowSize, int gridSize) {
        this.canvas = new Canvas(windowSize, windowSize);
        this.gc = canvas.getGraphicsContext2D();
        this.cellSize = (int) (windowSize / gridSize);
    }

    /**
     * Renderizza tutte le moto presenti nella lista.
     */
    public void renderAll(List<Position> players, List<Obstacle> trails, int gridW, int gridH) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // --- DISEGNO SCIE ---
        for (Obstacle t : trails) {
            Color c = getPlayerColor(t.getOwnerId());
            // La scia è più scura e sottile della moto (effetto estetico)
            gc.setFill(c.deriveColor(0, 1, 0.5, 0.5)); // 50% luminosità, 50% opacità
            gc.fillRect(t.getX() * cellSize + 2, t.getY() * cellSize + 2, cellSize - 4, cellSize - 4);
        }

        // --- DISEGNO MOTO ---
        for (Position p : players) {
            gc.setFill(getPlayerColor(p.getPlayerId()));
            gc.fillRect(p.getX() * cellSize + 1, p.getY() * cellSize + 1, cellSize - 2, cellSize - 2);

            gc.setStroke(Color.WHITE);
            gc.setLineWidth(1);
            gc.strokeRect(p.getX() * cellSize + 2, p.getY() * cellSize + 2, cellSize - 4, cellSize - 4);
        }
    }

    private Color getPlayerColor(int id) {
        return switch (id) {
            case 1 -> Color.web("#00FBFF"); // Cyan
            case 2 -> Color.web("#FF00FF"); // Magenta
            case 3 -> Color.web("#ADFF2F"); // GreenYellow
            case 4 -> Color.web("#FFD700"); // Gold
            default -> Color.WHITE;
        };
    }

    private void drawPlayer(Position p) {
        // Assegnazione colore dinamica basata sull'ID
        Color playerColor = switch (p.getPlayerId()) {
            case 1 -> Color.web("#00FBFF"); // Cyan
            case 2 -> Color.web("#FF00FF"); // Magenta
            case 3 -> Color.web("#ADFF2F"); // GreenYellow
            case 4 -> Color.web("#FFD700"); // Gold
            default -> Color.WHITE;
        };

        // Corpo della moto
        gc.setFill(playerColor);
        gc.fillRect(p.getX() * cellSize + 1, p.getY() * cellSize + 1, cellSize - 2, cellSize - 2);

        // Nucleo luminoso
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1);
        gc.strokeRect(p.getX() * cellSize + 2, p.getY() * cellSize + 2, cellSize - 4, cellSize - 4);
    }

    public void showGameOver() {
        gc.setFill(Color.gray(0, 0.6));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setFill(Color.RED);
        gc.setFont(new Font("System Bold", 40));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("GAME OVER", canvas.getWidth() / 2, canvas.getHeight() / 2);
    }

    public Canvas getCanvas() {
        return canvas;
    }
}