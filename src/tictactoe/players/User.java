package tictactoe.players;

import tictactoe.Field;
import tictactoe.players.Player;

import java.util.InputMismatchException;
import java.util.Scanner;

public class User implements Player {
    @Override
    public void move(Field field) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(field);

            System.out.print("Enter coordinates: ");
            try {
                int x = scanner.nextInt();
                int y = scanner.nextInt();

                if (x < 1 || x > 3 || y < 1 || y > 3) {
                    System.out.println("Coordinates should be from 1 to 3!");
                } else if (field.isFree(x, y)) {
                    field.set(x, y);
                    break;
                } else {
                    System.out.println("This cell is occupied! Choose another one!");
                }
            } catch (InputMismatchException e) {
                System.out.println("You should enter numbers!");
            }
        }
    }
}