package tic_tac_toe.players;

import tic_tac_toe.*;

import java.util.Random;

public interface Player {
    // Method to get the type of player you want.
    // Using a static method so it can always be called.
    static Player of(String type) {
        switch (type) {
            case "easy":
                return new EasyAI();
            case "medium":
                return new MediumAI();
            case "hard":
                return new HardAI();
            case "user":
                return new User();
            default:
                return null;
        }
    }

    void move(Field field);

    // Defining default methods that always apply to all implementations of the Player Class.
    default void moveRandomly(Field field) {
        Random random = new Random();
        int x, y;

        do {
            x = random.nextInt(3) + 1;
            y = random.nextInt(3) + 1;
        } while (!field.isFree(x, y));

        field.set(x, y);
    }

    default boolean checkNextTurnWin(Field field, Symbol symbol) {
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                if (field.willWin(i, j, symbol)) {
                    field.set(i, j);
                    return true;
                }
            }
        }
        return false;
    }
}