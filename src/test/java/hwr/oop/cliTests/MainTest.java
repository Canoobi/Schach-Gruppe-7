package hwr.oop.cliTests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import hwr.oop.chess.cli.Main;
import org.junit.jupiter.api.Test;

class MainTest {

  @Test
  void main_CanBeCalled() {
    assertDoesNotThrow(() -> Main.main(new String[] {}));
  }
}
