package game_of_life;

public class GenerationAlgorithm {
    private final Universe universe;

    GenerationAlgorithm(Universe universe) {
        this.universe = universe;
    }

    public void createNextGeneration() {
        Boolean[][] nextGeneration = universe.getMapCopy();
        int size = universe.getSize();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int amountOfNeighbours = countOfNeighbours(row, col);
                if (universe.getCell(row, col)) {
                    nextGeneration[row][col] = amountOfNeighbours == 2 || amountOfNeighbours == 3;
                } else {
                    nextGeneration[row][col] = amountOfNeighbours == 3;
                }
            }
        }
        universe.setGenerationCount(universe.getGenerationCount() + 1);
        universe.setMap(nextGeneration);
    }

    private int countOfNeighbours(int x, int y) {
        int size = universe.getSize();
        int count = 0;
        for (int k = -1; k <= 1; k++) {
            for (int l = -1; l <= 1; l++) {
                if (k == 0 && l == 0) {
                    continue;
                }

                int row = (x + k) % size;
                int column = (y + l) % size;

                int nextX = (row < 0) ? (row + size ) : row;
                int nextY = (column < 0) ? (column + size) : column;
                if (universe.getCell(nextX, nextY)) {
                    count++;
                }
            }
        }

        return count;
    }
}
