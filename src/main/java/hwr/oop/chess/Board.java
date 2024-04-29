package hwr.oop.chess;

import java.util.*;
import java.util.logging.Logger;

public class Board {
  private List<List<Piece>> playBoard;
  private Map<Character, Piece.PieceType> charToPieceType;

  private Map<Character, Character> abbrToFenChar;
  private Logger logger = Logger.getLogger(getClass().getName());

  public Board() {
    charToPieceType();
    abbrToFenChar();
    playBoard = new ArrayList<>(8);

    for (int i = 0; i < 8; i++) {
      playBoard.add(new ArrayList<>(8));
    }

    for (List<Piece> row : playBoard) {
      for (int i = 0; i < 8; i++) {
        row.add(null);
      }
    }
  }

  public Piece getPieceAt(int column, int row) {
    return this.playBoard.get(row).get(column);
  }

  public List<List<Piece>> getPlayBoard() {
    return playBoard;
  }

  public void setPieceAt(int column, int row, Piece piece) {
    this.playBoard.get(row).set(column, piece);
    if (piece != null) {
      piece.setActPosition(List.of(column, row));
    }
  }

  public char pieceToFenChar(Piece piece) {
    if (piece.getColor() == Piece.Color.WHITE) {
      return Character.toUpperCase(abbrToFenChar.get(piece.getAbbr()));
    } else {
      return abbrToFenChar.get(piece.getAbbr());
    }
  }

  public String getFenOfBoard() {
    int spaces = 0;

    StringBuilder fen = new StringBuilder();

    for (List<Piece> l : playBoard.reversed()) {
      for (Piece p : l) {
        if (p == null) {
          spaces++;
        } else {
          if (spaces > 0) {
            fen.append(spaces);
            spaces = 0;
          }
          fen.append(pieceToFenChar(p));
        }
      }
      if (spaces > 0) {
        fen.append(spaces);
        spaces = 0;
      }
      fen.append("/");
    }

    return fen.substring(0, fen.length() - 1);
  }

  public void setBoardToFen(String fen) {
    int column = 0;
    int row = 7;

    for (char c : fen.toCharArray()) {

      if (c == '/') {
        row--;
        column = 0;
      } else if (c >= '1' && c <= '8') {
        for (int i = column; i < column + (c - '0'); i++) {
          setPieceAt(i, row, null);
        }
        column += (c - '0');
      } else if (Character.isAlphabetic(c)) {
        setPieceAt(
            column,
            row,
            new Piece(
                charToPieceType.get(Character.toLowerCase(c)),
                List.of(column, row),
                Character.isUpperCase(c) ? Piece.Color.WHITE : Piece.Color.BLACK));

        column++;
      }
    }
  }

  public void initBoard() {
    setBoardToFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
  }

  public void charToPieceType() {
    charToPieceType = new HashMap<>();
    charToPieceType.put('r', Piece.PieceType.TURM);
    charToPieceType.put('n', Piece.PieceType.SPRINGER);
    charToPieceType.put('b', Piece.PieceType.LAEUFER);
    charToPieceType.put('q', Piece.PieceType.DAME);
    charToPieceType.put('k', Piece.PieceType.KOENIG);
    charToPieceType.put('p', Piece.PieceType.BAUER);
  }

  public void abbrToFenChar() {
    abbrToFenChar = new HashMap<>();
    abbrToFenChar.put('d', 'q');
    abbrToFenChar.put('s', 'n');
    abbrToFenChar.put('l', 'b');
    abbrToFenChar.put('t', 'r');
    abbrToFenChar.put('k', 'k');
    abbrToFenChar.put('b', 'p');
  }

  public void changePos(int oldCol, int oldRow, int newCol, int newRow) {
    this.playBoard.get(newRow).set(newCol, playBoard.get(oldRow).get(oldCol));
    if (getPieceAt(oldCol, oldRow) != null) {
      getPieceAt(oldCol, oldRow).setActPosition(List.of(newCol, newRow));
    }
    this.playBoard.get(oldRow).set(oldCol, null);
  }

  public void printBoard() {
    logger.info("Printing Board here");
  }

  public boolean isValidMove(Piece piece, int column, int row) {
    int vecX = column - piece.getActPosition().get(0);
    int vecY = row - piece.getActPosition().get(1);
    if (piece.isMoveRepeatable()) {
      return isValidMoveRepeat(piece, vecX, vecY);
    } else {
      if (piece.getAbbr() == 'b') {
        return isValidMovePawn(piece, vecX, vecY);
      } else {
        return isValidMoveNonRepeat(piece, vecX, vecY);
      }
    }
  }

  private boolean isValidMoveRepeat(Piece piece, int vecX, int vecY) {
    for (List<Integer> move : piece.getPosMoves()) {
      for (int j = -7; j < 8; j++) {
        if (j == 0) {
          continue;
        }
        if (move.getFirst() * j == vecX && move.get(1) * j == vecY) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean isValidMoveNonRepeat(Piece piece, int vecX, int vecY) {
    for (List<Integer> move : piece.getPosMoves()) {
      if (move.getFirst() == vecX && move.get(1) == vecY) {
        return true;
      }
    }
    return false;
  }

  private boolean isValidMovePawn(Piece piece, int vecX, int vecY) {
    if (piece.getColor() == Piece.Color.WHITE) {
      if (piece.getActPosition().get(1) == 1 && 0 == vecX && 2 == vecY) {
        return true;
      }
      return 0 == vecX && 1 == vecY;
    } else {
      if (piece.getActPosition().get(1) == 6 && 0 == vecX && -2 == vecY) {
        return true;
      }
      return 0 == vecX && -1 == vecY;
    }
  }

  public boolean isBlocked(Piece piece, int newColumn, int newRow) {
    List<Integer> oldPos = piece.getActPosition();
    List<Integer> vec = Arrays.asList(newColumn - oldPos.getFirst(), newRow - oldPos.get(1));
    if (vec.getFirst() < 0) {
      vec.set(0, -1);
    }
    if(vec.getFirst()>0){
      vec.set(0, 1);
    }

    if (vec.get(1) < 0) {
      vec.set(1, -1);
    }
    if(vec.get(1) > 0){
      vec.set(1, 1);
    }

    for (int i = 1;
        i < ((newColumn - oldPos.getFirst()) * vec.getFirst())
            || i < ((newRow - oldPos.get(1)) * vec.get(1));
        i++) {
      if (this.playBoard
              .get(oldPos.get(1) + i * vec.get(1))
              .get(oldPos.getFirst() + i * vec.getFirst())
          != null) {
        return true;
      }
    }
    return false;
  }
}
