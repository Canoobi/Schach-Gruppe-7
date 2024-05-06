package hwr.oop.chess;

public class Game {
  private final int id;
  private Board board;
  private Piece.Color activePlayer;

  public Game(int id, String fen, Piece.Color activePlayer){
    this.id = id;
    this.board = new Board();
    board.setBoardToFen(fen);
    this.activePlayer = activePlayer;
  }

  public Game(int id){
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
  public void movePiece(int oldCol, int oldRow, int newCol, int newRow)
  {
    newBoard.changePos(this.color, oldCol, oldRow, newCol, newRow);
  }
}
