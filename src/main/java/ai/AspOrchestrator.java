package ai;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import model.*;

import java.util.List;
import java.util.Map;

/**
 * Orchestratore: Gestisce il ciclo di vita del ragionamento per più agenti.
 */
public class AspOrchestrator {
    private final Handler handler;
    private final FactGenerator factGenerator;
    private final ResultHandler resultHandler;
    private final String logicFilePath = "src/main/resources/asp/tron.lp";

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

        // Caricamento della logica ASP fissa
        ASPInputProgram logic = new ASPInputProgram();
        logic.addFilesPath(logicFilePath);
        handler.addProgram(logic);

        // Generazione e caricamento dei fatti dinamici
        handler.addProgram(factGenerator.getFacts(width, height, positions, obstacles));

        // Esecuzione Sincrona
        Output output = handler.startSync();
        AnswerSets answerSets = (AnswerSets) output;

        // Ora passiamo tutto al nostro nuovo ResultHandler basato su Regex
        return resultHandler.getActions(answerSets);
    }
}