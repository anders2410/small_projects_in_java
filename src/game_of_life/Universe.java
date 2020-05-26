package game_of_life;

import game_of_life.interfaces.Model;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class Universe implements Model {
    private final int size;
    private int generationCount;
    private Boolean[][] map;


    public Universe(int size) {
        this.size = size;
        map = new Boolean[size][size];
        generationCount = 0;
        Random random = new Random();
        for (Boolean[] row : map) {
            Arrays.setAll(row, it -> random.nextBoolean());
        }
    }

    public Boolean[][] getMapCopy() {
        Boolean[][] copy = new Boolean[size][size];
        for (int i = 0; i < map.length; i++) {
            copy[i] = Arrays.copyOf(map[i], map[i].length);
        }
        return copy;
    }

    public int getAllAlive() {
        int count = 0;
        for (Boolean[] row : map) {
            count += Arrays.stream(row).filter(i -> i).count();
        }
        return count;
    }

    @Override
    public String toString() {
        StringBuilder rows = new StringBuilder();
        for (Boolean[] row : map) {
            rows.append(Arrays.stream(row)
                    .map(it -> it ? "O" : " ")
                    .collect(Collectors.joining()));
            rows.append("\n");
        }
        return rows.toString();
    }

    public int getSize() {
        return size;
    }

    public Boolean getCell(int i, int j) {
        return map[i][j];
    }

    public void setCell(int i, int j, boolean newValue) {
        map[i][j] = newValue;
    }

    public int getGenerationCount() {
        return generationCount;
    }

    public void setGenerationCount(int generationCount) {
        this.generationCount = generationCount;
    }

    public Boolean[][] getMap() {
        return map;
    }

    public void setMap(Boolean[][] map) {
        this.map = map;
    }
}
