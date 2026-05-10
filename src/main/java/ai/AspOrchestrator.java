package ai;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import model.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Orchestratore: Gestisce il ciclo di vita del ragionamento per più agenti.
 */
public class AspOrchestrator {
    private final Handler handler;
    private final FactGenerator factGenerator;
    private final ResultHandler resultHandler;


    public AspOrchestrator(String solverPath) {
        // Inizializzazione DLV2 e Handler
        this.handler = new DesktopHandler(new DLV2DesktopService(solverPath));
        this.factGenerator = new FactGenerator();
        this.resultHandler = new ResultHandler();

        try {
            // Registrazione classi per il mapping riflessivo
            ASPMapper.getInstance().registerClass(GridSize.class);
            ASPMapper.getInstance().registerClass(Position.class);
            ASPMapper.getInstance().registerClass(MoveDecision.class);
            ASPMapper.getInstance().registerClass(Obstacle.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Calcola le mosse per tutte le moto considerando anche le scie (obstacles).
     * @param width Larghezza griglia
     * @param height Altezza griglia
     * @param positions Lista delle posizioni attuali delle moto
     * @param obstacles Lista delle scie lasciate (ostacoli)
     * @return Mappa ID Moto -> Direzione scelta
     */
    public Map<Integer, String> computeNextMoves(int width, int height, List<Position> positions, List<Obstacle> obstacles) {
        // Pulisce l'handler dai fatti del turno precedente
        handler.removeAll();

        // Carica il programma logico ASP
        ASPInputProgram logic = new ASPInputProgram();
        try (InputStream in = getClass().getResourceAsStream("/asp/tron.lp")) {
            if (in == null) {
                throw new IllegalStateException("Risorsa /asp/tron.lp non trovata nel classpath");
            }
            String program = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            logic.addProgram(program);
        } catch (IOException e) {
            throw new RuntimeException("Impossibile leggere tron.lp", e);
        }
        handler.addProgram(logic);

        // Genera e aggiunge i fatti dello stato corrente al solver
        ASPInputProgram facts = factGenerator.getFacts(width, height, positions, obstacles);
        handler.addProgram(facts);

        // Esegue il solver
        Output output = handler.startSync();

        if (!(output instanceof AnswerSets answerSets)) {
            System.err.println("ASP error: output is not AnswerSets, type=" + output.getClass().getName());
            return Collections.emptyMap();
        }
        return resultHandler.getActions(answerSets);
    }
}