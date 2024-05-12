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

  @Test
  void loadGameTest() {
    Game game = new Game(5, "8/pppppppp/8/8/8/8/8/8", Piece.Color.BLACK);
    assertSoftly(
        softly -> {
          softly.assertThat(game.getId()).isEqualTo(5);
          softly.assertThat(game.getActivePlayer()).isEqualTo(Piece.Color.BLACK);
          softly.assertThat(game.getBoard().getFenOfBoard()).isEqualTo("8/pppppppp/8/8/8/8/8/8");
        });
  }

  @Test
  void getWinnerTest() {
    Game game = new Game(5);
    game.setWinner("White");
    assertSoftly(
        softly -> {
          softly.assertThat(game.getWinner()).isEqualTo("White");
        });
  }

  @Test
  void movePieceWhiteTest() {
    Game game = new Game(5);
    game.movePiece(1, 1, 1, 2);
    assertSoftly(
        softly -> {
          softly.assertThat(game.getActivePlayer()).isEqualTo(Piece.Color.BLACK);
          softly
              .assertThat(game.getBoard().getFenOfBoard())
              .isEqualTo("rnbqkbnr/pppppppp/8/8/8/1P6/P1PPPPPP/RNBQKBNR");
        });
  }

  @Test
  void movePieceBlackTest() {
    Game game = new Game(5);
    game.setActivePlayer(Piece.Color.BLACK);
    game.movePiece(5, 6, 5, 5);
    assertSoftly(
        softly -> {
          softly.assertThat(game.getActivePlayer()).isEqualTo(Piece.Color.WHITE);
          softly
              .assertThat(game.getBoard().getFenOfBoard())
              .isEqualTo("rnbqkbnr/ppppp1pp/5p2/8/8/8/PPPPPPPP/RNBQKBNR");
        });
  }
}
