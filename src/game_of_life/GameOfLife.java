package game_of_life;

import game_of_life.interfaces.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

/**
 * GameOfLife acts as the view (in an MVC sense) of the Game of Life.
 */
public class GameOfLife extends JFrame implements View {
    private final GameOfLifeController controller;
    // generationLabel conveys the current generation.
    private final JLabel generationLabel = new JLabel();
    // numAliveLabel conveys the number of currently living cells.
    private final JLabel aliveLabel = new JLabel();
    // gameCells maintains the
    private final JPanel[][] cells;

    private final int SIZE = 20;

    /**
     * Constructs a new GameOfLife object.
     */
    public GameOfLife() {
        super("Game of Life");
        controller = new GameOfLifeController(new Universe(20), this);
        setSize(1000, 1000);

        //Statistics Panel for Generation and alive statistics
        JPanel statisticsPanel = new JPanel();
        statisticsPanel.setLayout(new BoxLayout(statisticsPanel, BoxLayout.Y_AXIS));
        generationLabel.setName("GenerationLabel");
        aliveLabel.setName("AliveLabel");
        generationLabel.setText("Generation #0");
        aliveLabel.setText("Alive: 0");
        statisticsPanel.add(generationLabel);
        statisticsPanel.add(aliveLabel);

        //Button Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        JToggleButton playPauseButton = new JToggleButton("Play");
        playPauseButton.setName("PlayToggleButton");
        playPauseButton.addItemListener(itemEvent -> {
            int state = itemEvent.getStateChange();
            if (state == ItemEvent.SELECTED) {
                playPauseButton.setText("Pause");
                controller.play();
            } else {
                playPauseButton.setText("Play");
                controller.pause();
            }
        });
        JButton resetButton = new JButton("Reset");
        resetButton.setName("ResetButton");
        resetButton.addActionListener(actionEvent -> controller.reset());


        buttonPanel.add(playPauseButton);
        buttonPanel.add(resetButton);

        // controlPanel for buttons and statistics
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        statisticsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        controlPanel.add(buttonPanel);
        controlPanel.add(statisticsPanel);

        // Universe Panel to show the actual universe
        JPanel universePanel = new JPanel();
        universePanel.setLayout(new GridLayout(SIZE, SIZE));
        cells = new JPanel[SIZE][SIZE];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                JPanel cell = new JPanel();
                cell.setBorder(BorderFactory.createLineBorder(Color.black));
                universePanel.add(cell);
                cells[i][j] = cell;
            }
        }

        // JFrame
        setLayout(new BorderLayout());
        add(controlPanel, BorderLayout.WEST);
        add(universePanel, BorderLayout.CENTER);
        controller.setView();
        setVisible(true);
    }

    public void updateView(Boolean[][] world, long currGeneration, int numAlive) {
        // Reset the view of the world.
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world.length; j++) {
                this.cells[i][j].setBackground(world[i][j] ? Color.BLACK : null);
            }
        }

        // Reset the view of the current generation.
        this.generationLabel.setText(String.format("Generation: %d", currGeneration));

        // Reset the view of the number of living cells.
        this.aliveLabel.setText(String.format("Alive: %d", numAlive));
    }
}
