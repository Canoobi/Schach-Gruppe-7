package hwr.oop.chess;

import java.util.List;

public class Piece {
  public enum Color {
    BLACK,
    WHITE
  }

  public enum PieceType {
    BAUER('b', List.of((new Position(0, 1))), false),
    TURM('t', List.of(new Position(0, 1), new Position(1, 0)), true),
    SPRINGER(
        's',
        List.of(
            new Position(2, 1),
            new Position(2, -1),
            new Position(1, 2),
            new Position(-1, 2),
            new Position(-2, 1),
            new Position(-2, -1),
            new Position(1, -2),
            new Position(-1, -2)),
        false),
    LAEUFER('l', List.of(new Position(1, 1), new Position(-1, 1)), true),
    KOENIG(
        'k',
        List.of(
            new Position(0, 1),
            new Position(1, 0),
            new Position(1, 1),
            new Position(-1, 1),
            new Position(0, -1),
            new Position(-1, 0),
            new Position(-1, -1),
            new Position(-1, -1)),
        false),
    DAME(
        'd',
        List.of(new Position(0, 1), new Position(1, 0), new Position(1, 1), new Position(-1, 1)),
        true);

    private final char abbreviation;
    private final List<Position> moves;
    private final boolean moveRepeatable;

    PieceType(char abbreviation, List<Position> moves, boolean moveRepeatable) {
      this.abbreviation = abbreviation;
      this.moves = moves;
      this.moveRepeatable = moveRepeatable;
    }

    public char getAbbreviation() {
      return abbreviation;
    }

    public List<Position> getMoves() {
      return moves;
    }

    public boolean isMoveRepeatable() {
      return moveRepeatable;
    }
  }

  private final PieceType pieceType;
  private Position actualPosition;
  private final Color color;

  public Piece(PieceType pieceType, Position position, Color color) {
    this.pieceType = pieceType;
    this.actualPosition = position;
    this.color = color;
  }

  public void setActualPosition(Position actualPosition) {
    this.actualPosition = actualPosition;
  }

  public Color getColor() {
    return this.color;
  }

  public char getAbbreviation() {
    return this.pieceType.getAbbreviation();
  }

  public Position getActualPosition() {
    return this.actualPosition;
  }

  public List<Position> getPossibleMoves() {
    return this.pieceType.getMoves();
  }

  public boolean isMoveRepeatable() {
    return pieceType.isMoveRepeatable();
  }
}
