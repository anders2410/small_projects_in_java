package game_of_life;

import game_of_life.interfaces.Controller;

/**
 * GameOfLifeController acts as the controller (in an MVC sense) of the Game of Life.
 */
public class GameOfLifeController extends Thread implements Controller {
    // model is the game's model data.
    private Universe model;
    // view is the game's view data.
    private final GameOfLife view;

    private GenerationAlgorithm generationAlgorithm;
    private boolean paused;

    /**
     * Constructs a new GameOfLifeController object.
     *
     * @param model The game's model.
     * @param view  The game's view.
     */
    public GameOfLifeController(Universe model, GameOfLife view) {
        this.model = model;
        this.view = view;
        paused = true;
        generationAlgorithm = new GenerationAlgorithm(model);
    }

    public void run(){
        while (!interrupted()) {
            try {
                while(!interrupted() && paused) {
                    Thread.sleep(100);
                }
                generationAlgorithm.createNextGeneration();
                setView();
                Thread.sleep(200);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Dispose of the view JFrame.
        view.setVisible(false);
        view.dispose();
    }

    public void setView(){
        view.updateView(model.getMap(), model.getGenerationCount(), model.getAllAlive());
    }

    public void play() {
        if(this.getState() == State.NEW){
            this.start();
        }
        paused = false;
    }

    public void pause() {
        paused = true;
    }

    public void reset() {
        this.model = new Universe(model.getSize());
        generationAlgorithm = new GenerationAlgorithm(model);
        setView();
    }
}
