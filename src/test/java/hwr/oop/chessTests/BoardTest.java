package hwr.oop.chessTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import hwr.oop.chess.Board;
import hwr.oop.chess.Piece;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BoardTest {
  @Test
  void isValidMoveTest() {
    Board board = new Board();
    board.initBoard();
    Piece bauer1 = board.getPieceAt(1, 1);
    Piece bauer2 = board.getPieceAt(3, 6);
    Piece turm = board.getPieceAt(7, 0);
    Piece koenig = board.getPieceAt(4, 0);
    Piece laeufer = board.getPieceAt(5, 0);

    assertSoftly(
        softly -> {
          softly.assertThat(board.isValidMove(bauer1, 1, 2)).isTrue();
          softly.assertThat(board.isValidMove(bauer1, 1, 3)).isTrue();
          softly.assertThat(board.isValidMove(bauer1, 0, 3)).isFalse();
          softly.assertThat(board.isValidMove(bauer1, 0, 4)).isFalse();
          softly.assertThat(board.isValidMove(bauer2, 3, 5)).isTrue();
          softly.assertThat(board.isValidMove(bauer2, 3, 4)).isTrue();
          softly.assertThat(board.isValidMove(bauer2, 0, 3)).isFalse();
          softly.assertThat(board.isValidMove(bauer2, 0, 4)).isFalse();
          softly.assertThat(board.isValidMove(turm, 7, 4)).isTrue();
          softly.assertThat(board.isValidMove(turm, 7, 7)).isTrue();
          softly.assertThat(board.isValidMove(turm, 7, 8)).isFalse();
          softly.assertThat(board.isValidMove(turm, 0, 3)).isFalse();
          softly.assertThat(board.isValidMove(turm, 0, 4)).isFalse();
          softly.assertThat(board.isValidMove(koenig, 3, 0)).isTrue();
          softly.assertThat(board.isValidMove(koenig, 3, 1)).isTrue();
          softly.assertThat(board.isValidMove(koenig, 4, 1)).isTrue();
          softly.assertThat(board.isValidMove(koenig, 7, 1)).isFalse();
          softly.assertThat(board.isValidMove(koenig, 4, 3)).isFalse();
          softly.assertThat(board.isValidMove(koenig, 7, 3)).isFalse();
          softly.assertThat(board.isValidMove(laeufer, 0, 5)).isTrue();
        });
  }

  @Test
  void isBlockedTest() {
    Board board = new Board();
    board.initBoard();

    assertSoftly(
        softly -> {
          softly.assertThat(board.isBlocked(board.getPieceAt(0, 0), 0, 6)).isTrue();
          softly.assertThat(board.isBlocked(board.getPieceAt(2, 0), 4, 2)).isTrue();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 0), 4, 2)).isTrue();
        });
  }

  @Test
  void isBlockedTestFullBoard() {
    Board board = new Board();
    board.setBoardToFen("pppppppp/pppppppp/pppqpppp/ppppqppp/pppppppp/pppppppp/pppppppp/pppppppp");

    assertSoftly(
        softly -> {
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 4, 2)).isTrue();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 4, 6)).isTrue();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 6, 4)).isTrue();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 2, 4)).isTrue();

          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 6, 6)).isTrue();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 2, 2)).isTrue();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 2, 6)).isTrue();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 6, 2)).isTrue();
        });
  }

  @Test
  void isBlockedTestEmptyBoard() {
    Board board = new Board();
    board.setBoardToFen("8/8/8/4q3/8/8/8/8");

    assertSoftly(
        softly -> {
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 4, 2)).isFalse();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 4, 6)).isFalse();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 6, 4)).isFalse();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 2, 4)).isFalse();

          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 6, 6)).isFalse();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 2, 2)).isFalse();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 2, 6)).isFalse();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 6, 2)).isFalse();
        });
  }

  @Test
  void getKingTest() {
    Board board = new Board();
    board.setBoardToFen("8/8/8/8/8/8/8/kK6");

    assertSoftly(
        softly -> {
          softly
              .assertThat(board.getKing(Piece.Color.WHITE).getColor())
              .isEqualTo(Piece.Color.WHITE);
          softly
              .assertThat(board.getKing(Piece.Color.WHITE).getActPosition().getFirst())
              .isEqualTo(1);
        });
  }

  @Test
  void getKingNullTest() {
    Board board = new Board();
    board.setBoardToFen("8/8/8/8/8/8/8/8");

    assertSoftly(
        softly -> {
          softly.assertThat(board.getKing(Piece.Color.WHITE)).isNull();
        });
  }

  @Test
  void isCheckTest() {
    Board board = new Board();
    board.setBoardToFen("k7/2Q5/1N5b/Q7/8/8/3r4/2K5");

    assertSoftly(
        softly -> {
          softly.assertThat(board.isCheck(Piece.Color.WHITE)).isFalse();
          softly.assertThat(board.isCheck(Piece.Color.BLACK)).isTrue();
        });
  }

  @Test
  void isBlockedTestPieceOnEdgeOfMovement() {
    Board board = new Board();
    board.setBoardToFen("8/2P1P1P1/8/2P1q1P1/8/2P1P1P1/8/8");
    assertSoftly(
        softly -> {
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 4, 2)).isFalse();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 4, 6)).isFalse();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 6, 4)).isFalse();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 2, 4)).isFalse();

          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 6, 6)).isFalse();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 2, 2)).isFalse();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 2, 6)).isFalse();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 6, 2)).isFalse();
        });
  }

  @Test
  void isBlockedTestHalfFilledBoard() {
    Board board = new Board();
    board.setBoardToFen("pppppppp/pppppppp/pppppppp/ppppqppp/8/8/8/8");
    assertSoftly(
        softly -> {
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 2, 2)).isFalse();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 4, 2)).isFalse();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 6, 2)).isFalse();

          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 2, 4)).isTrue();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 6, 4)).isTrue();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 2, 6)).isTrue();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 4, 6)).isTrue();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 6, 6)).isTrue();
        });

    board.setBoardToFen("4pppp/4pppp/4pppp/4qppp/4pppp/4pppp/4pppp/4pppp");
    assertSoftly(
        softly -> {
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 2, 2)).isFalse();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 2, 4)).isFalse();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 2, 6)).isFalse();

          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 4, 2)).isTrue();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 4, 6)).isTrue();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 6, 2)).isTrue();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 6, 4)).isTrue();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 6, 6)).isTrue();
        });
    board.setBoardToFen("pppp4/pppp4/pppp4/ppppq3/pppp4/pppp4/pppp4/pppp4");
    assertSoftly(
        softly -> {
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 4, 2)).isFalse();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 4, 6)).isFalse();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 6, 2)).isFalse();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 6, 4)).isFalse();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 6, 6)).isFalse();

          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 2, 2)).isTrue();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 2, 4)).isTrue();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 2, 6)).isTrue();
        });
    board.setBoardToFen("8/8/8/ppppqppp/pppppppp/pppppppp/pppppppp/pppppppp");
    assertSoftly(
        softly -> {
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 2, 6)).isFalse();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 4, 6)).isFalse();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 6, 6)).isFalse();

          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 2, 2)).isTrue();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 4, 2)).isTrue();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 6, 2)).isTrue();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 2, 4)).isTrue();
          softly.assertThat(board.isBlocked(board.getPieceAt(4, 4), 6, 4)).isTrue();
        });
    board.setBoardToFen("pppp4/pppp4/pppp4/pppp4/pppq4/pppp4/pppp4/pppp4");
    assertSoftly(
        softly -> {
          softly.assertThat(board.isBlocked(board.getPieceAt(3, 3), 5, 1)).isFalse();
          softly.assertThat(board.isBlocked(board.getPieceAt(3, 3), 5, 3)).isFalse();
          softly.assertThat(board.isBlocked(board.getPieceAt(3, 3), 5, 5)).isFalse();

          softly.assertThat(board.isBlocked(board.getPieceAt(3, 3), 1, 5)).isTrue();
          softly.assertThat(board.isBlocked(board.getPieceAt(3, 3), 3, 5)).isTrue();
          softly.assertThat(board.isBlocked(board.getPieceAt(3, 3), 3, 1)).isTrue();
          softly.assertThat(board.isBlocked(board.getPieceAt(3, 3), 1, 1)).isTrue();
          softly.assertThat(board.isBlocked(board.getPieceAt(3, 3), 1, 3)).isTrue();
        });
  }

  @Test
  void setBoardToFenTest() {
    Board board = new Board();

    board.setBoardToFen("r7/8/8/2kb4/4K3/8/8/7r");
    assertSoftly(
        softly -> {
          softly
              .assertThat(board.getPieceAt(3, 4).getAbbr())
              .isEqualTo(Piece.PieceType.LAEUFER.getAbbr());
          softly.assertThat(board.getPieceAt(3, 4).getColor()).isEqualTo(Piece.Color.BLACK);
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
  void getFenOfBoardTest() {
    Board board = new Board();
    board.setBoardToFen("1rr5/8/8/8/4K3/8/8/6rr");

    Assertions.assertThat(board.getFenOfBoard()).isEqualTo("1rr5/8/8/8/4K3/8/8/6rr");
  }

  @Test
  void setPieceAtTest() {
    Board board = new Board();
    board.initBoard();
    Piece piece = board.getPieceAt(0, 0);
    board.setPieceAt(3, 5, piece);

    assertSoftly(
        softly -> {
          softly.assertThat(board.getPieceAt(3, 5).getActPosition().getFirst()).isEqualTo(3);
          softly.assertThat(board.getPieceAt(3, 5).getActPosition().get(1)).isEqualTo(5);
        });
  }

  @Test
  void getPieceAtTest() {
    Board board = new Board();
    board.initBoard();

    assertSoftly(
        softly -> {
          softly.assertThat(board.getPieceAt(7, 0).getAbbr()).isEqualTo('t');
          softly.assertThat(board.getPieceAt(7, 0).getActPosition().getFirst()).isEqualTo(7);
          softly.assertThat(board.getPieceAt(7, 0).getColor()).isEqualTo(Piece.Color.WHITE);
        });
  }

  @Test
  void printBoardTest() {
    Board board = new Board();
    board.initBoard();
    board.printBoard();
    assertThat(board.getPieceAt(4, 4)).isNull();
  }

  @Test
  void testBoard() {
    Board board = new Board();

    assertThat(board.getPlayBoard()).hasSize(8);
    // TODO assertions nur am Ende
    for (List<Piece> row : board.getPlayBoard()) {
      assertThat(row).hasSize(8);
      for (Object piece : row) {
        assertThat(piece).isNull();
      }
    }
  }

  @Test
  void initBoardTest() {
    // TODO Assertions nur am Ende
    Board board = new Board();
    board.initBoard();

    int row = 0;
    int column;
    for (List<Piece> pieces : board.getPlayBoard()) {
      column = 0;
      for (Piece piece : pieces) {
        try {
          assertThat(piece.getActPosition()).isEqualTo(List.of(column, row));
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

    assertSoftly(
        softly -> {
          softly.assertThat(board.getPieceAt(1, 0)).isNull();
          softly.assertThat(piece).isEqualTo(board.getPieceAt(0, 2));
          softly.assertThat(piece.getActPosition().getFirst()).isEqualTo(0);
          softly.assertThat(piece.getActPosition().get(1)).isEqualTo(2);
        });
  }
}
