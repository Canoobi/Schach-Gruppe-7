package hwr.oop.chessTests;

import hwr.oop.chess.Piece;

import hwr.oop.chess.Position;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class PieceTest {
  @Test
  void isMoveRepeatableTest() {
    Piece turm = new Piece(Piece.PieceType.TURM, new Position(0, 0), Piece.Color.BLACK);
    Piece laeufer = new Piece(Piece.PieceType.LAEUFER, new Position(0, 0), Piece.Color.BLACK);
    Piece dame = new Piece(Piece.PieceType.DAME, new Position(0, 0), Piece.Color.BLACK);
    Piece springer = new Piece(Piece.PieceType.SPRINGER, new Position(0, 0), Piece.Color.BLACK);

    assertSoftly(
        softly -> {
          softly.assertThat(turm.isMoveRepeatable()).isTrue();
          softly.assertThat(laeufer.isMoveRepeatable()).isTrue();
          softly.assertThat(dame.isMoveRepeatable()).isTrue();
          softly.assertThat(springer.isMoveRepeatable()).isFalse();
        });
  }

  @Test
  void getActualPositionTest() {
    Piece turm = new Piece(Piece.PieceType.TURM, new Position(1, 0), Piece.Color.BLACK);
    Piece laeufer = new Piece(Piece.PieceType.LAEUFER, new Position(0, 1), Piece.Color.BLACK);
    Piece dame = new Piece(Piece.PieceType.DAME, new Position(4, 2), Piece.Color.BLACK);
    Piece springer = new Piece(Piece.PieceType.SPRINGER, new Position(6, 1), Piece.Color.BLACK);

    assertSoftly(
        softly -> {
          softly.assertThat(turm.getActualPosition()).isEqualTo(new Position(1, 0));
          softly.assertThat(laeufer.getActualPosition()).isEqualTo(new Position(0, 1));
          softly.assertThat(dame.getActualPosition()).isEqualTo(new Position(4, 2));
          softly.assertThat(springer.getActualPosition()).isEqualTo(new Position(6, 1));
        });
  }

  @Test
  void setActualPositionTest() {
    Piece turm = new Piece(Piece.PieceType.TURM, new Position(1, 0), Piece.Color.BLACK);
    Piece laeufer = new Piece(Piece.PieceType.LAEUFER, new Position(0, 1), Piece.Color.BLACK);

    turm.setActualPosition(new Position(4, 3));
    laeufer.setActualPosition(new Position(2, 6));

    assertSoftly(
        softly -> {
          softly.assertThat(turm.getActualPosition()).isEqualTo(new Position(4, 3));
          softly.assertThat(laeufer.getActualPosition()).isEqualTo(new Position(2, 6));
        });
  }
}
