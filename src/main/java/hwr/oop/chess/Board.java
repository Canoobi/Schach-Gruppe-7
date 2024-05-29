package hwr.oop.chess;

import java.util.*;

public class Board {
  private final List<List<Piece>> playBoard;
  private Map<Character, Piece.PieceType> charToPieceType;

  private Map<Character, Character> abbreviationToFenChar;

  public Board() {
    charToPieceType();
    abbreviationToFenChar();
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
      piece.setActualPosition(new Position(column, row));
    }
  }

  public char pieceToFenChar(Piece piece) {
    if (piece.getColor() == Piece.Color.WHITE) {
      return Character.toUpperCase(abbreviationToFenChar.get(piece.getAbbreviation()));
    } else {
      return abbreviationToFenChar.get(piece.getAbbreviation());
    }
  }

  public FENString getFenOfBoard() {
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

    return new FENString(fen.substring(0, fen.length() - 1));
  }

  public void setBoardToFen(FENString fenString) {

    String fen = fenString.value();
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
                new Position(column, row),
                Character.isUpperCase(c) ? Piece.Color.WHITE : Piece.Color.BLACK));

        column++;
      }
    }
  }

  public void initBoard() {
    setBoardToFen(new FENString("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR"));
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

  public void abbreviationToFenChar() {
    abbreviationToFenChar = new HashMap<>();
    abbreviationToFenChar.put('d', 'q');
    abbreviationToFenChar.put('s', 'n');
    abbreviationToFenChar.put('l', 'b');
    abbreviationToFenChar.put('t', 'r');
    abbreviationToFenChar.put('k', 'k');
    abbreviationToFenChar.put('b', 'p');
  }

  public void changePosition(int oldCol, int oldRow, int newCol, int newRow) {
    this.playBoard.get(newRow).set(newCol, playBoard.get(oldRow).get(oldCol));
    if (getPieceAt(oldCol, oldRow) != null) {
      getPieceAt(oldCol, oldRow).setActualPosition(new Position(newCol, newRow));
    }
    this.playBoard.get(oldRow).set(oldCol, null);
  }

  public Piece getKing(Piece.Color color) {
    for (List<Piece> l : playBoard) {
      for (Piece p : l) {
        if (p != null
            && p.getColor() == color
            && Character.toLowerCase(p.getAbbreviation()) == 'k') {
          return p;
        }
      }
    }
    return null;
  }

  public boolean isCheck(Piece.Color color) {
    Piece king = getKing(color);
    int kingX = king.getActualPosition().getX();
    int kingY = king.getActualPosition().getY();

    for (List<Piece> l : playBoard) {
      for (Piece p : l) {
        if (p != null
            && p.getColor() != color
            && isValidMove(p, kingX, kingY)
            && !(isBlocked(p, kingX, kingY))) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean isValidMove(Piece piece, int column, int row) {
    int vecX = column - piece.getActualPosition().getX();
    int vecY = row - piece.getActualPosition().getY();
    if (piece.isMoveRepeatable()) {
      return isValidMoveRepeat(piece, vecX, vecY);
    } else {
      if (piece.getAbbreviation() == 'b') {
        if (getPieceAt(column, row) != null) {
          return canCapturePawn(piece, column, row);
        } else {
          return isValidMovePawn(piece, vecX, vecY);
        }
      } else {
        return isValidMoveNonRepeat(piece, vecX, vecY);
      }
    }
  }

  private boolean isValidMoveRepeat(Piece piece, int vecX, int vecY) {
    for (Position move : piece.getPossibleMoves()) {
      for (int j = -7; j < 8; j++) {
        if (j == 0) {
          continue;
        }
        if (move.getX() * j == vecX && move.getY() * j == vecY) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean isValidMoveNonRepeat(Piece piece, int vecX, int vecY) {
    for (Position move : piece.getPossibleMoves()) {
      if (move.getX() == vecX && move.getY() == vecY) {
        return true;
      }
    }
    return false;
  }

  private boolean isValidMovePawn(Piece piece, int vecX, int vecY) {
    if (piece.getColor() == Piece.Color.WHITE) {
      if (piece.getActualPosition().getY() == 1 && 0 == vecX && 2 == vecY) {
        return true;
      }
      return 0 == vecX && 1 == vecY;
    } else {
      if (piece.getActualPosition().getY() == 6 && 0 == vecX && -2 == vecY) {
        return true;
      }
      return 0 == vecX && -1 == vecY;
    }
  }

  public boolean canCapture(Piece piece, int vecX, int vecY) {
    if (piece.getAbbreviation() == 'b') {
      return canCapturePawn(piece, vecX, vecY);
    }

    if (getPieceAt(vecX, vecY) == null) {
      return true;
    } else {
      return piece.getColor() != getPieceAt(vecX, vecY).getColor();
    }
  }

  public boolean canCapturePawn(Piece piece, int vecX, int vecY) {
    if (piece.getColor() == Piece.Color.WHITE) {
      if (Math.abs(piece.getActualPosition().getX() - vecX) != 1) {
        return getPieceAt(vecX, vecY) == null && piece.getActualPosition().getY() - vecY == -1;
      } else {
        return piece.getActualPosition().getY() - vecY == -1
            && piece.getColor() != getPieceAt(vecX, vecY).getColor();
      }
    } else {
      if (Math.abs(piece.getActualPosition().getX() - vecX) != 1) {
        return getPieceAt(vecX, vecY) == null && piece.getActualPosition().getY() - vecY == 1;
      } else {
        return piece.getActualPosition().getY() - vecY == 1
            && piece.getColor() != getPieceAt(vecX, vecY).getColor();
      }
    }
  }

  public boolean isBlocked(Piece piece, int newColumn, int newRow) {
    Position oldPos = piece.getActualPosition();
    List<Integer> vector = Arrays.asList(newColumn - oldPos.getX(), newRow - oldPos.getY());
    if (vector.getFirst() < 0) {
      vector.set(0, -1);
    }
    if (vector.getFirst() > 0) {
      vector.set(0, 1);
    }

    if (vector.get(1) < 0) {
      vector.set(1, -1);
    }
    if (vector.get(1) > 0) {
      vector.set(1, 1);
    }

    for (int i = 1;
        i < ((newColumn - oldPos.getX()) * vector.getFirst())
            || i < ((newRow - oldPos.getY()) * vector.get(1));
        i++) {
      if (this.playBoard
              .get(oldPos.getY() + i * vector.get(1))
              .get(oldPos.getX() + i * vector.getFirst())
          != null) {
        return true;
      }
    }
    return false;
  }

  public boolean isCorrectColor(Piece.Color color, int column, int row) {
    return this.playBoard.get(row).get(column).getColor() == color;
  }

  public boolean directionContainsLegalMove(Piece piece, Position direction) {
    int positionX = piece.getActualPosition().getX();
    int positionY = piece.getActualPosition().getY();
    Piece deletedPiece;

    for (int i = 1;
        i * direction.getX() + positionX >= 0
            && i * direction.getX() + positionX < 8
            && i * direction.getY() + positionY >= 0
            && i * direction.getY() + positionY < 8;
        i++) {
      if (isValidMove(piece, i * direction.getX() + positionX, i * direction.getY() + positionY)
          && !isBlocked(
              piece, i * direction.getX() + positionX, i * direction.getY() + positionY)) {
        if ((getPieceAt(i * direction.getX() + positionX, i * direction.getY() + positionY) != null)
            && (getPieceAt(i * direction.getX() + positionX, i * direction.getY() + positionY)
                    .getColor()
                == piece.getColor())) {
          continue;
        }
        deletedPiece =
            playBoard.get(i * direction.getX() + positionX).get(i * direction.getY() + positionY);
        changePosition(
            positionX,
            positionY,
            i * direction.getX() + positionX,
            i * direction.getY() + positionY);
        if (!isCheck(piece.getColor())) {
          changePosition(
              i * direction.getX() + positionX,
              i * direction.getY() + positionY,
              positionX,
              positionY);
          playBoard
              .get(i * direction.getX() + positionX)
              .set(i * direction.getY() + positionY, deletedPiece);
          return true;
        }
        changePosition(
            i * direction.getX() + positionX,
            i * direction.getY() + positionY,
            positionX,
            positionY);
        playBoard
            .get(i * direction.getX() + positionX)
            .set(i * direction.getY() + positionY, deletedPiece);
      }
    }
    return false;
  }

  public boolean pieceHasLegalMoves(Piece piece) {
    if (piece == null) {
      return false;
    }
    for (Position move : piece.getPossibleMoves()) {
      if (directionContainsLegalMove(piece, move)
          || directionContainsLegalMove(piece, new Position(move.getX() * (-1), move.getY() * (-1)))) {
        return true;
      }
    }
    return false;
  }

  public boolean legalMovesPossible(Piece.Color playerColor) {
    for (List<Piece> row : playBoard) {
      for (Piece piece : row) {
        if (piece == null) {
          continue;
        }
        if (piece.getColor() == playerColor && pieceHasLegalMoves(piece)) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean stalemate(Piece.Color playercolor) {
    return !isCheck(playercolor) && !legalMovesPossible(playercolor);
  }

  public boolean checkmate(Piece.Color playercolor) {
    return isCheck(playercolor) && !legalMovesPossible(playercolor);
  }
}
