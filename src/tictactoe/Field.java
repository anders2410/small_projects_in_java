package tictactoe;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Field {
    private Symbol[][] field;
    private boolean xIsNext;
    private Symbol winner;

    public Field() {
        xIsNext = true;
        field = new Symbol[][]{
                {Symbol.EMPTY, Symbol.EMPTY, Symbol.EMPTY},
                {Symbol.EMPTY, Symbol.EMPTY, Symbol.EMPTY},
                {Symbol.EMPTY, Symbol.EMPTY, Symbol.EMPTY}
        };
        winner = null;
    }

    public boolean isFree(int x, int y) {
        int column = x - 1;
        int row = 3 - y;

        return field[row][column] == Symbol.EMPTY;
    }

    public boolean isFree(Point point) {
        return isFree(point.getX(), point.getY());
    }

    public void set(Point point) {
        set(point.getX(), point.getY());
    }

    public void set(int x, int y) {
        if (!isFree(x, y) || winner != null) {
            throw new IllegalStateException();
        }

        int column = x - 1;
        int row = 3 - y;

        field[row][column] = nextSymbol();
        xIsNext = !xIsNext;

        winner = checkWinner();
    }

    public void unset(int x, int y) {
        if (isFree(x, y)) {
            throw new IllegalStateException();
        }

        int column = x - 1;
        int row = 3 - y;

        field[row][column] = Symbol.EMPTY;
        xIsNext = !xIsNext;

        winner = checkWinner();
    }

    private Symbol checkWinner() {
        if (checkRows(Symbol.X) || checkColumns(Symbol.X) || checkDiagonals(Symbol.X)) {
            return Symbol.X;
        } else if (checkRows(Symbol.O) || checkColumns(Symbol.O) || checkDiagonals(Symbol.O)) {
            return Symbol.O;
        } else if (isFilled()) {
            return Symbol.EMPTY;
        }
        return null;
    }

    public boolean willWin(int x, int y, Symbol symbol) {
        int column = x - 1;
        int row = 3 - y;

        if (isFree(x, y)) {
            Symbol before = field[row][column];
            field[row][column] = symbol;

            boolean won = checkWinner() == symbol;

            field[row][column] = before;
            return won;
        }
        return false;
    }

    public Symbol nextSymbol() {
        return xIsNext ? Symbol.X : Symbol.O;
    }

    private boolean checkRows(Symbol symbol) {
        for (int i = 0; i < 3; i++) {
            if (field[i][0] == symbol && field[i][1] == symbol && symbol == field[i][2]) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColumns(Symbol symbol) {
        for (int i = 0; i < 3; i++) {
            if (field[0][i] == symbol && field[1][i] == symbol && field[2][i] == symbol) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonals(Symbol symbol) {
        return (field[1][1] == symbol) &&
                (((field[0][0] == symbol) && (field[2][2] == symbol)) ||
                        ((field[0][2] == symbol) && (field[2][0] == symbol)));
    }

    private boolean isFilled() {
        return Arrays.stream(field)
                .flatMap(Arrays::stream)
                .noneMatch(symbol -> symbol == Symbol.EMPTY);
    }

    public boolean continues() {
        return winner == null;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("---------")
                .append(System.lineSeparator());

        for (Symbol[] row : field) {
            String line = Arrays.stream(row).map(Symbol::getSymbol).collect(Collectors.joining(" "));

            stringBuilder.append("| ")
                    .append(line)
                    .append((" |"))
                    .append(System.lineSeparator());
        }

        stringBuilder.append("---------");
        return stringBuilder.toString();
    }

    public Symbol getWinner() {
        return winner;
    }
}