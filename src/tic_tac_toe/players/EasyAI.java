package tic_tac_toe.players;

import tic_tac_toe.Field;

public class EasyAI implements Player {

    @Override
    public void move(Field field) {
        System.out.println(field);
        System.out.println("Making move level \"easy\"");

        moveRandomly(field);
    }
}
