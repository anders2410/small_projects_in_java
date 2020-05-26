package main;

import minesweeper.Minesweeper;
import tic_tac_toe.TicTacToe;

public class Main {
    public static void main(String[] args) {
        Game minesweeper = new Minesweeper(9,9);
        Game tictactoe = new TicTacToe();
        tictactoe.startGame();
        //minesweeper.startGame();
    }
}
