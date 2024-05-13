package hwr.oop.chess;

import java.util.List;

public class Piece {
  public enum Color {
    BLACK,
    WHITE
  }

  public enum PieceType {
    BAUER('b', List.of((List.of(0, 1))), false),
    TURM('t', List.of(List.of(0, 1), List.of(1, 0)), true),
    SPRINGER(
        's',
        List.of(
            List.of(2, 1),
            List.of(2, -1),
            List.of(1, 2),
            List.of(-1, 2),
            List.of(-2, 1),
            List.of(-2, -1),
            List.of(1, -2),
            List.of(-1, -2)),
        false),
    LAEUFER('l', List.of(List.of(1, 1), List.of(-1, 1)), true),
    KOENIG(
        'k',
        List.of(
            List.of(0, 1),
            List.of(1, 0),
            List.of(1, 1),
            List.of(-1, 1),
            List.of(0, -1),
            List.of(-1, 0),
            List.of(-1, -1),
            List.of(-1, -1)),
        false),
    DAME('d', List.of(List.of(0, 1), List.of(1, 0), List.of(1, 1), List.of(-1, 1)), true);

    private final char abbreviation;
    private final List<List<Integer>> moves;
    private final boolean moveRepeatable;

    PieceType(char abbreviation, List<List<Integer>> moves, boolean moveRepeatable) {
      this.abbreviation = abbreviation;
      this.moves = moves;
      this.moveRepeatable = moveRepeatable;
    }

    public char getAbbreviation() {
      return abbreviation;
    }

    public List<List<Integer>> getMoves() {
      return moves;
    }

    public boolean isMoveRepeatable() {
      return moveRepeatable;
    }
  }

  private List<Integer> actualPosition;
  private List<List<Integer>> possibleMoves;
  private boolean moveRepeatable;
  private Color color;
  private char abbreviation;

  public Piece(PieceType pieceType, List<Integer> pos, Color color) {
    this.possibleMoves = pieceType.getMoves();
    this.moveRepeatable = pieceType.isMoveRepeatable();
    this.abbreviation = pieceType.getAbbreviation();
    this.actualPosition = pos;
    this.color = color;
  }

  public void setActualPosition(List<Integer> actualPosition) {
    this.actualPosition = actualPosition;
  }

  public Color getColor() {
    return this.color;
  }

  public char getAbbreviation() {
    return this.abbreviation;
  }

  public List<Integer> getActualPosition() {
    return this.actualPosition;
  }

  public List<List<Integer>> getPossibleMoves() {
    return this.possibleMoves;
  }

  public boolean isMoveRepeatable() {
    return moveRepeatable;
  }
}
