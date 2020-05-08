package minesweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final int SIDE = 9;
    private static final GameObject[][] gameField = new GameObject[SIDE][SIDE];
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();
    private static boolean isFirstTurn = true;
    private static boolean steppedOnMine = false;
    private static boolean minesCreated = false;

    public static void main(String[] args) {
        System.out.print("How many mines do you want on the field? ");
        int numOfMines = scanner.nextInt();

        createGame();
        displayField();

        while (true) {
            System.out.println("Set/delete mines marks (x and y coordinates): ");
            int xInput = scanner.nextInt() - 1;
            int yInput = scanner.nextInt() - 1;
            String action = scanner.next();

            if (action.equals("mine")) {
                gameField[yInput][xInput].isFlag = !gameField[yInput][xInput].isFlag;
            } else if (action.equals("free")) {
                if (isFirstTurn) {
                    createMines(numOfMines, yInput, xInput);
                    isFirstTurn = false;
                }

                if (gameField[yInput][xInput].isMine) {
                    steppedOnMine = true;
                    displayField();
                    System.out.println("You stepped on a mine and failed!");
                    break;
                }

                if (gameField[yInput][xInput].countMineNeighbors > '0') {
                    gameField[yInput][xInput].isOpen = true;
                } else {
                    exploreMinefield(yInput, xInput);
                }
            }

            displayField();
            if (isGameWon()) {
                System.out.println("Congratulations! You found all mines!");
                break;
            }

        }
    }

    private static void exploreMinefield(int yInput, int xInput) {
        gameField[yInput][xInput].isFree = true;
        gameField[yInput][xInput].isOpen = true;
        for (GameObject neighbour : getNeighbors(gameField[yInput][xInput])) {
            if (neighbour.countMineNeighbors == '0' && !neighbour.isFree && !neighbour.isMine) {
                neighbour.isFree = true;
                neighbour.isOpen = true;
                exploreMinefield(neighbour.y, neighbour.x);
            } else if (neighbour.countMineNeighbors > '0' && !neighbour.isFree && !neighbour.isMine) {
                gameField[neighbour.y][neighbour.x].isOpen = true;
            }
        }
    }

    public static void displayField() {
        System.out.println(" |123456789|");
        System.out.println("-|---------|");
        for (int row = 0; row < 9; row++) {
            System.out.print((row + 1) + "|");
            for (int col = 0; col < 9; col++) {
                GameObject g = gameField[row][col];
                if (isFirstTurn) {
                    System.out.print(g.isFlag ? '*' : g.isFree ? '/' : '.');
                } else if (steppedOnMine) {
                    if (g.isMine) {
                        System.out.print('X');
                    } else {
                        whichCharToPrint(g);
                    }
                } else {
                    whichCharToPrint(g);
                }
            }
            System.out.println("|");
        }
        System.out.println("-|---------|");
    }

    private static void whichCharToPrint(GameObject g) {
        if (g.isOpen) {
            if (g.countMineNeighbors > '0') {
                System.out.print(g.countMineNeighbors);
            } else if (g.isFree) {
                System.out.print('/');
            } else if (g.isFlag) {
                System.out.print('*');
            } else {
                System.out.print('.');
            }
        } else if (g.isFlag) {
            boolean flag = false;
            for (GameObject neighbour : getNeighbors(g)) {
                if (neighbour.isFree) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                System.out.print('/');
            } else {
                System.out.print('*');
            }
        } else {
            System.out.print('.');
        }
    }

    public static void createGame() {
        for (int row = 0; row < SIDE; row++) {
            for (int col = 0; col < SIDE; col++) {
                gameField[row][col] = new GameObject(col, row, false);
            }
        }
    }

    private static void createMines(int mines, int y, int x) {
        minesCreated = true;
        for (int i = 0; i < mines; i++) {
            int col = random.nextInt(SIDE);
            int row = random.nextInt(SIDE);
            while (true) {
                if (gameField[row][col].isMine || (y == row && x == col)) {
                    col = random.nextInt(SIDE);
                    row = random.nextInt(SIDE);
                } else {
                    gameField[row][col].isMine = true;
                    break;
                }
            }
        }
        countMineNeighbors();
    }

    public static boolean isGameWon() {
        if (minesCreated) {
            boolean firstWinCondition = true;
            boolean secondWinCondition = true;
            for (GameObject[] ob : gameField) {
                for (GameObject g1 : ob) {
                    if (!g1.isOpen && !g1.isMine) {
                        firstWinCondition = false;
                        break;
                    }
                }
                for (GameObject g : ob) {
                    if (g.isFlag && !g.isMine || g.isMine && !g.isFlag) {
                        secondWinCondition = false;
                        break;
                    }
                }
            }
            return firstWinCondition || secondWinCondition;
        }
        return false;
    }

    private static void countMineNeighbors() {
        for (int row = 0; row < SIDE; row++) {
            for (int col = 0; col < SIDE; col++) {
                if (!gameField[row][col].isMine) {
                    List<GameObject> result = getNeighbors(gameField[row][col]);
                    for (GameObject neighbor : result) {
                        if (neighbor.isMine) {
                            gameField[row][col].countMineNeighbors++;
                        }
                    }
                }
            }
        }
    }

    private static List<GameObject> getNeighbors(GameObject gameObject) {
        List<GameObject> result = new ArrayList<>();
        for (int row = gameObject.y - 1; row <= gameObject.y + 1; row++) {
            for (int col = gameObject.x - 1; col <= gameObject.x + 1; col++) {
                if (row < 0 || row >= SIDE) {
                    continue;
                }
                if (col < 0 || col >= SIDE) {
                    continue;
                }
                if (gameField[row][col] == gameObject) {
                    continue;
                }
                result.add(gameField[row][col]);
            }
        }
        return result;
    }
}


