package hwr.oop.chessTests;

import hwr.oop.chess.Game;
import hwr.oop.chess.Piece;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class GameTest {
  @Test
  void newGameTest() {
    Game game = new Game(1);
    assertSoftly(
        softly -> {
          softly.assertThat(game.getId()).isEqualTo(1);
          softly.assertThat(game.getActivePlayer()).isEqualTo(Piece.Color.WHITE);
          softly
              .assertThat(game.getBoard().getFenOfBoard())
              .isEqualTo("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
        });
  }
}
