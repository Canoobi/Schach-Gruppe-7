package classTests;

import classes.Board;
import classes.Piece;
import java.util.ArrayList;
import java.util.Arrays;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BoardTest {
  @Test
  void isValidMoveTest() {
    Board board = new Board();
    board.initBoard();
    Piece piece = board.getPieceAt(0, 1); // weisser bauer
    Assertions.assertThat(board.isValidMove(piece, 0, 2))
        .isTrue(); // bauer bewegt sich eins nach vorn
    Assertions.assertThat(board.isValidMove(piece, 0, 3))
        .isFalse(); // bauer kann nicht seitlich gehen
  }

  @Test
  void isBlockedTest() {
    Board board = new Board();
    board.initBoard();
    Assertions.assertThat(board.isBlocked(board.getPieceAt(0, 0), 0, 6)).isTrue();
    Assertions.assertThat(board.isBlocked(board.getPieceAt(2, 0), 4, 2)).isTrue();
  }

  @Test
  void setBoardToFenTest() {
    Board board = new Board();

    board.setBoardToFen("r7/8/8/2kb4/4K3/8/8/7r");

    Assertions.assertThat(board.getPieceAt(3, 4).getAbbr())
        .isEqualTo(Piece.PieceType.LAEUFER.getAbbr());
    Assertions.assertThat(board.getPieceAt(3, 4).getColor()).isEqualTo(Piece.Color.BLACK);

    Assertions.assertThat(board.getPieceAt(0, 0)).isNull();

    board.setBoardToFen("1r6/8/8/8/4K3/8/8/8");
    Assertions.assertThat(board.getPieceAt(7, 0)).isNull();
    Assertions.assertThat(board.getPieceAt(0, 7)).isNull();
  }

  @Test
  void getPieceAtTest() {
    Board board = new Board();
    board.initBoard();

    Assertions.assertThat(board.getPieceAt(7, 0).getAbbr()).isEqualTo('t');
    Assertions.assertThat(board.getPieceAt(7, 0).getActPosition().get(1)).isEqualTo(7);
    Assertions.assertThat(board.getPieceAt(7, 0).getColor()).isEqualTo(Piece.Color.WHITE);
  }

  @Test
  void testBoard() {
    Board board = new Board();

    Assertions.assertThat(board.getPlayBoard()).hasSize(8);

    for (ArrayList row : board.getPlayBoard()) {
      Assertions.assertThat(row).hasSize(8);
      for (Object piece : row) {
        Assertions.assertThat(piece).isNull();
      }
    }
  }

  @Test
  void initBoardTest() {
    Board board = new Board();
    board.initBoard();
    int row = 0;
    int column = 0;
    for (ArrayList<Piece> pieces : board.getPlayBoard()) {
      column = 0;

      for (Piece piece : pieces) {

        try {
          Assertions.assertThat(piece.getActPosition()).isEqualTo(Arrays.asList(row, column));
        } catch (NullPointerException e) {
          Assertions.assertThat(piece).isNull();
        }
        column++;
      }
      row++;
    }
  }

  @Test
  void changePosTest() {
    Board board = new Board();
    board.initBoard();
    Piece piece = board.getPieceAt(0, 1);
    Piece targetPiece = board.getPieceAt(2, 0);
    Piece originalPiece = board.getPieceAt(0, 1);

    board.changePos(1, 0, 0, 2);

    Assertions.assertThat(board.getPieceAt(0, 1)).isNull();
    Assertions.assertThat(piece).isEqualTo(board.getPieceAt(2, 0));
  }
}
