package hwr.oop.chessTests;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import hwr.oop.chess.FENString;
import hwr.oop.chess.Game;
import hwr.oop.chess.Piece;
import org.junit.jupiter.api.Test;

class GameTest {
  @Test
  void newGameTest() {
    Game game = new Game(1);
    assertSoftly(
        softly -> {
          softly.assertThat(game.getId()).isEqualTo(1);
          softly.assertThat(game.getActivePlayer()).isEqualTo(Piece.Color.WHITE);
          softly.assertThat(game.getBoard().getFenOfBoard()).isEqualTo(new FENString("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR"));
        });
  }

  @Test
  void loadGameTest() {
    Game game = new Game(5, new FENString("8/pppppppp/8/8/8/8/8/8"), Piece.Color.BLACK);
    assertSoftly(
        softly -> {
          softly.assertThat(game.getId()).isEqualTo(5);
          softly.assertThat(game.getActivePlayer()).isEqualTo(Piece.Color.BLACK);
          softly.assertThat(game.getBoard().getFenOfBoard()).isEqualTo(new FENString("8/pppppppp/8/8/8/8/8/8"));
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
  void movePossibleCaptureFriendly() {
    Game game = new Game(6);
    game.getBoard().setBoardToFen(new FENString("3R4/8/8/8/2K1B3/3P4/1N6/8"));

    assertSoftly(
        softly -> {
          softly.assertThat(game.movePossible(1, 1, 2, 3)).isFalse();
          softly.assertThat(game.movePossible(3, 2, 2, 3)).isFalse();
          softly.assertThat(game.movePossible(3, 7, 3, 2)).isFalse();
          softly.assertThat(game.movePossible(2, 3, 3, 2)).isFalse();
        });
  }

  @Test
  void movePossibleCaptureEnemy() {
    Game game = new Game(6);
    game.getBoard().setBoardToFen(new FENString("7K/8/8/3p4/2N1P3/1q2P3/8/8"));

    assertSoftly(
        softly -> {
          softly.assertThat(game.movePossible(3, 4, 2, 3)).isTrue();
          softly.assertThat(game.movePossible(1, 2, 2, 3)).isTrue();
          softly.assertThat(game.movePossible(1, 2, 4, 2)).isTrue();
        });
  }

  @Test
  void movePossible() {
    Game game = new Game(6);
    game.getBoard().setBoardToFen(new FENString("7b/8/8/8/3P4/8/8/B7"));

    assertSoftly(
        softly -> {
          softly.assertThat(game.movePossible(0, 0, 7, 7)).isFalse();
        });
  }

  @Test
  void movePossibleMoveIntoCheck() {
    Game game = new Game(7);
    game.getBoard().setBoardToFen(new FENString("r7/8/8/8/8/8/B7/K7"));

    assertSoftly(
        softly -> {
          softly.assertThat(game.movePossible(0, 1, 1, 2)).isFalse();
        });
  }

  @Test
  void movePossibleCheck() {
    Game game = new Game(7);
    game.getBoard().setBoardToFen(new FENString("8/8/r7/8/8/8/1r6/K7"));

    assertSoftly(
        softly -> {
          softly.assertThat(game.movePossible(0, 0, 0, 1)).isFalse();
          softly.assertThat(game.movePossible(0, 0, 1, 0)).isFalse();
          softly.assertThat(game.movePossible(0, 0, 1, 1)).isTrue();
        });
  }

  @Test
  void movePieceWhiteTest() {
    Game game = new Game(5);
    game.movePiece(1, 1, 1, 2);
    assertSoftly(
        softly -> {
          softly.assertThat(game.getActivePlayer()).isEqualTo(Piece.Color.BLACK);
          softly.assertThat(game.getBoard().getFenOfBoard()).isEqualTo(new FENString("rnbqkbnr/pppppppp/8/8/8/1P6/P1PPPPPP/RNBQKBNR"));
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
          softly.assertThat(game.getBoard().getFenOfBoard()).isEqualTo(new FENString("rnbqkbnr/ppppp1pp/5p2/8/8/8/PPPPPPPP/RNBQKBNR"));
        });
  }
}
