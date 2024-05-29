package hwr.oop.chess;

public record Position(int xCoordinate, int yCoordinate) {
    public int getX(){
        return xCoordinate;
    }

    public int getY(){
        return yCoordinate;
    }
}
