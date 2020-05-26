package tic_tac_toe;

import main.Game;
import tic_tac_toe.players.Player;

import java.util.Scanner;

public class TicTacToe implements Game {
    @Override
    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        Player[] players = new Player[2];

        String command;
        do {
            System.out.print("Input command: ");
            command = scanner.nextLine();
            String[] arguments = command.split("\\s+");

            if ("exit".equals(command)) {
                break;
            } else if (arguments.length != 3 || !"start".equals(arguments[0])) {
                System.out.println("Bad parameters!");
                continue;
            }

            players[0] = Player.of(arguments[1]);
            players[1] = Player.of(arguments[2]);

            if (players[0] == null || players[1] == null) {
                System.out.println("Bad parameters!");
                continue;
            }

            loopGame(players[0], players[1]);
            System.out.println();
        } while (true);
    }

    private static void loopGame(Player xPlayer, Player oPlayer) {
        Field field = new Field();

        while (field.continues()) {
            if (field.nextSymbol() == Symbol.X) {
                xPlayer.move(field);
            } else {
                oPlayer.move(field);
            }
        }
        System.out.println(field);
        Symbol winner = field.getWinner();

        switch (winner) {
            case X:
            case O:
                System.out.println(winner.getSymbol() + " wins");
                break;
            case EMPTY:
                System.out.println("Draw");
                break;
            default:
                throw new IllegalStateException();
        }
    }
}
