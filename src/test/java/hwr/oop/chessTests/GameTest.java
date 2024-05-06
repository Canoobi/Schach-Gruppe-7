package hwr.oop.chessTests;

import hwr.oop.chess.Board;
import hwr.oop.chess.Game;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class GameTest {
  @Test
  void testNewBoard() {
    Game game = new Game(123);
    Assertions.assertThat(game.getBoard()).isNotNull();
  }
}
