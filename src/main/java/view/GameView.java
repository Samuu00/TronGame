package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
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
    public void renderAll(List<Position> players, int gridW, int gridH) {
        // Reset Sfondo
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Disegno Griglia
        gc.setStroke(Color.web("#0D1117"));
        gc.setLineWidth(0.5);
        for (int i = 0; i <= gridW; i++) {
            gc.strokeLine(i * cellSize, 0, i * cellSize, canvas.getHeight());
            gc.strokeLine(0, i * cellSize, canvas.getWidth(), i * cellSize);
        }

        // Disegno ogni Giocatore
        for (Position p : players) {
            drawPlayer(p);
        }
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