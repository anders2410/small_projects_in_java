package tictactoe.players;

import tictactoe.Field;
import tictactoe.Symbol;
import tictactoe.players.Player;

public class MediumAI implements Player {
    @Override
    public void move(Field field) {
        System.out.println(field);
        System.out.println("Making move level \"medium\"");

        if (tryWin(field)) return;
        if (tryDefend(field)) return;

        moveRandomly(field);
    }

    private boolean tryWin(Field field) {
        Symbol symbol = field.nextSymbol();
        return checkNextTurnWin(field, symbol);
    }

    private boolean tryDefend(Field field) {
        Symbol symbol = field.nextSymbol().opposite();
        return checkNextTurnWin(field, symbol);
    }
}
