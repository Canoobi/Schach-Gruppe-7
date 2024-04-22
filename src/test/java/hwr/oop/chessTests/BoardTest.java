package hwr.oop.chessTests;

import hwr.oop.chess.Board;
import hwr.oop.chess.Piece;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class BoardTest {
  @Test
  void isValidMoveTest() {
    Board board = new Board();
    board.initBoard();
    Piece piece = board.getPieceAt(1, 0); // weisser bauer

    assertSoftly(
        softly -> {
          softly
              .assertThat(board.isValidMove(piece, 0, 2))
              .isTrue(); // bauer bewegt sich eins nach vorn
          softly
              .assertThat(board.isValidMove(piece, 0, 3))
              .isFalse(); // bauer kann nicht seitlich gehen
        });
  }

  @Test
  void isBlockedTest() {
    Board board = new Board();
    board.initBoard();

    assertSoftly(
        softly -> {
          softly.assertThat(board.isBlocked(board.getPieceAt(0, 0), 0, 6)).isTrue();
          softly.assertThat(board.isBlocked(board.getPieceAt(0, 2), 4, 2)).isTrue();
        });
  }

  @Test
  void setBoardToFenTest() {
    Board board = new Board();

    board.setBoardToFen("r7/8/8/2kb4/4K3/8/8/7r");
    assertSoftly(
        softly -> {
          softly.assertThat(board.getPieceAt(4, 3).getAbbr()).isEqualTo(Piece.PieceType.LAEUFER.getAbbr());
          softly.assertThat(board.getPieceAt(4, 3).getColor()).isEqualTo(Piece.Color.BLACK);
          softly.assertThat(board.getPieceAt(0, 0)).isNull();
        });

    board.setBoardToFen("1r6/8/8/8/4K3/8/8/8");
    assertSoftly(
        softly -> {
          softly.assertThat(board.getPieceAt(0, 7)).isNull();
          softly.assertThat(board.getPieceAt(7, 0)).isNull();
        });
  }

  @Test
  void getPieceAtTest() {
    Board board = new Board();
    board.initBoard();

    assertSoftly(softly -> {
      softly.assertThat(board.getPieceAt(0, 7).getAbbr()).isEqualTo('t');
      softly.assertThat(board.getPieceAt(0, 7).getActPosition().get(1)).isEqualTo(7);
      softly.assertThat(board.getPieceAt(0, 7).getColor()).isEqualTo(Piece.Color.WHITE);
    });
  }

  @Test
  void testBoard() {
    Board board = new Board();

    assertThat(board.getPlayBoard()).hasSize(8);
    //TODO assertions nur am Ende
    for (List<Piece> row : board.getPlayBoard()) {
      assertThat(row).hasSize(8);
      for (Object piece : row) {
        assertThat(piece).isNull();
      }
    }
  }

  @Test
  void initBoardTest() {
    //TODO Assertions nur am Ende
    Board board = new Board();
    board.initBoard();
    int row = 0;
    int column = 0;
    for (List<Piece> pieces : board.getPlayBoard()) {
      column = 0;

      for (Piece piece : pieces) {

        try {
          assertThat(piece.getActPosition()).isEqualTo(Arrays.asList(row, column));
        } catch (NullPointerException e) {
          assertThat(piece).isNull();
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
    Piece piece = board.getPieceAt(1, 0);
    board.changePos(1, 0, 0, 2);

    assertSoftly(softly -> {
      softly.assertThat(board.getPieceAt(1, 0)).isNull();
      softly.assertThat(piece).isEqualTo(board.getPieceAt(0, 2));
    });
  }
}