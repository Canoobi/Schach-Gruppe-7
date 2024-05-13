package hwr.oop.chess;

public class Game {
  private final int id;
  private Board board;
  private Piece.Color activePlayer;
  private String winner;

  public Game(int id, String fen, Piece.Color activePlayer) {
    this.id = id;
    this.board = new Board();
    board.setBoardToFen(fen);
    this.activePlayer = activePlayer;
  }

  public Game(int id) {
    this.id = id;
    this.board = new Board();
    board.initBoard();
    this.activePlayer = Piece.Color.WHITE;
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

  public void movePiece(int oldCol, int oldRow, int newCol, int newRow) {
    board.changePosition(this.getActivePlayer(), oldCol, oldRow, newCol, newRow);
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
