# TronGame

![Java](https://img.shields.io/badge/Java-21-blue.svg)
![JavaFX](https://img.shields.io/badge/JavaFX-21-orange.svg)
![Maven](https://img.shields.io/badge/Build-Maven-brightgreen.svg)
![ASP](https://img.shields.io/badge/Language-ASP-blue.svg)

Un'implementazione del classico gioco arcade **Tron**, sviluppata in **Java 21** e **JavaFX**. 
Il core engine è basato su un'architettura **MVC** stretta, con l'aggiunta di un layer di Intelligenza Artificiale basato su **Answer Set Programming (ASP)** per la gestione delle logiche decisionali dell'avversario.

## 🏗 Architettura e Stack Tecnologico

Il progetto è progettato seguendo i principi **SOLID** ed è modulato per garantire una facile estensibilità, separando la logica di dominio dalla presentazione e dal reasoning dell'AI.

* **Linguaggio:** Java 21
* **UI Framework:** JavaFX 21.0.6
* **Build Tool:** Maven
* **AI & Reasoning:** Answer Set Programming (ASP) per il calcolo delle path ottimali e logica decisionale.

### Project Structure

La codebase è organizzata per feature/layer:

```text
src/main/java/
├── ai/             # Layer di integrazione ASP
│   ├── ASPOrchestrator.java  # Gestione ciclo di vita del solver (es. DLV2 / Clingo)
│   ├── FactGenerator.java    # Traduzione dello stato del Model in fatti ASP
│   └── ResultHandler.java    # Parsing dei modelli generati dal solver
├── controller/     # Binding logico tra UI e stato di gioco
├── model/          # Entità di dominio, logica core e state management
└── view/           # Componenti grafici e rendering (JavaFX)
