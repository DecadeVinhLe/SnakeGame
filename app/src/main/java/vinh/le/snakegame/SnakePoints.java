package vinh.le.snakegame;

public class SnakePoints {
    private int positionX;
    private int positionY;
    public SnakePoints(int startPositionX, int startPositionY) {
        this.positionX = startPositionX;
        this.positionY = startPositionY;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionX(int newPositionX) {
        this.positionX = newPositionX;
    }

    public void setPositionY(int newPositionY) {
        this.positionY = newPositionY;
    }
}
