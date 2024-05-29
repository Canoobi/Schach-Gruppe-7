package hwr.oop.chess;

public class Game {
  private final int id;
  private final Board board;
  private Piece.Color activePlayer;
  private String winner;

  public Game(int id, FENString fen, Piece.Color activePlayer) {
    this.id = id;
    this.board = new Board();
    board.setBoardToFen(fen);
    this.activePlayer = activePlayer;
    this.winner = null;
  }

  public Game(int id) {
    this.id = id;
    this.board = new Board();
    board.initBoard();
    this.activePlayer = Piece.Color.WHITE;
    this.winner = null;
  }

  public int getId() {
    return id;
  }

  public Piece.Color getActivePlayer() {
    return activePlayer;
  }

  public void setActivePlayer(Piece.Color activePlayer) {
    this.activePlayer = activePlayer;
  }

  public Board getBoard() {
    return board;
  }

  public boolean movePossible(int oldCol, int oldRow, int newCol, int newRow) {
    if (!board.isValidMove(board.getPieceAt(oldCol, oldRow), newCol, newRow)) {
      return false;
    }
    if (!board.canCapture(board.getPieceAt(oldCol, oldRow), newCol, newRow)) {
      return false;
    }
    if (board.isBlocked(board.getPieceAt(oldCol, oldRow), newCol, newRow)) {
      return false;
    }

    Piece possibleDeleted = board.getPieceAt(newCol, newRow);
    board.setPieceAt(newCol, newRow, board.getPieceAt(oldCol, oldRow));
    board.setPieceAt(oldCol, oldRow, null);
    boolean check = board.isCheck(getActivePlayer());
    board.setPieceAt(oldCol, oldRow, board.getPieceAt(newCol, newRow));
    board.setPieceAt(newCol, newRow, possibleDeleted);
    return !check;
  }

  public void movePiece(int oldCol, int oldRow, int newCol, int newRow) {

    board.changePosition(oldCol, oldRow, newCol, newRow);

    if (this.getActivePlayer() == Piece.Color.WHITE) {
      setActivePlayer(Piece.Color.BLACK);
    } else {
      this.setActivePlayer(Piece.Color.WHITE);
    }
  }

  public String getWinner() {
    return winner;
  }

  public void setWinner(String winner) {
    this.winner = winner;
  }
}
