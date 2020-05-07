package minesweeper;

public class GameObject {
    public int x;
    public int y;
    public boolean isMine;
    public boolean isFree;
    public char countMineNeighbors;
    public boolean isOpen;
    public boolean isFlag;

    public GameObject(int x, int y, boolean isMine) {
        this.x = x;
        this.y = y;
        this.isMine = isMine;

        countMineNeighbors = '0';
        isOpen = false;
    }
}
