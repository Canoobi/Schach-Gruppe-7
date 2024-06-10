package hwr.oop.cliTests;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import hwr.oop.chess.cli.Main;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class MainTest {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @BeforeEach
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
  }

  @AfterEach
  public void restoreStreams() {
    System.setOut(originalOut);
  }

  @Test
  void main_CanBeCalled() {
    assertDoesNotThrow(() -> Main.main(new String[] {}));
  }

  @Test
  void main_CanBeCalledWithHelpCommand() {
    Main mainTest = new Main();
    System.out.println(mainTest);
    Main.main(new String[] {"help"});
    assertSoftly(
        softly -> softly
            .assertThat(outContent.toString())
            .contains("You can use one of the following commands:"));
  }
}
